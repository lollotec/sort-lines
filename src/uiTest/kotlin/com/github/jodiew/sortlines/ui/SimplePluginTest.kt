package com.github.jodiew.sortlines.ui

import com.intellij.driver.sdk.waitForIndicators
import com.intellij.ide.starter.buildTool.GradleBuildTool
import com.intellij.ide.starter.ci.CIServer
import com.intellij.ide.starter.ci.NoCIServer
import com.intellij.ide.starter.community.model.BuildType
import com.intellij.ide.starter.di.di
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.plugins.PluginConfigurator
import com.intellij.ide.starter.project.LocalProjectInfo
import com.intellij.ide.starter.runner.Starter
import com.intellij.openapi.util.SystemInfo
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.time.Duration.Companion.seconds

@Tag("ui")
class SimplePluginTest {
    init {
        di = DI {
            extend(di)
            bindSingleton<CIServer>(overrides = true) {
                object : CIServer by NoCIServer {
                    override fun reportTestFailure(
                        testName: String,
                        message: String,
                        details: String,
                        linkToLogs: String?
                    ) {
                        fail { "$testName fails: $message. \n$details" }
                    }
                }
            }
        }
    }

    @Test
    fun simpleTest() {
        val result = Starter.newContext(
            "testSimple",
            TestCase(
                IdeProductProvider.IU.copy(
                    buildNumber = System.getProperty("uiPlatformBuildVersion"),
                    buildType = BuildType.RELEASE.type,
                ),
                LocalProjectInfo(Path("src/uiTest/resources/test-projects/simple-project"))
            )
        ).apply {
            val pathToPlugin = System.getProperty("path.to.build.plugin")
            PluginConfigurator(this).installPluginFromPath(Paths.get(pathToPlugin))
            withBuildTool<GradleBuildTool>()
        }.applyVMOptionsPatch {
            // === Common system properties for all operating systems ===

            // Required JVM arguments for module access
            addSystemProperty("--add-opens", "java.base/java.lang=ALL-UNNAMED")
            addSystemProperty("--add-opens", "java.desktop/javax.swing=ALL-UNNAMED")

            // Core IDE configuration
            addSystemProperty("idea.trust.all.projects", true) // Trust all projects automatically
            addSystemProperty("jb.consents.confirmation.enabled", false) // Disable consent dialogs
            addSystemProperty("jb.privacy.policy.text", "<!--999.999-->") // Skip privacy policy
            addSystemProperty("ide.show.tips.on.startup.default.value", false) // No tips on startup

            // Test framework configuration
            addSystemProperty("junit.jupiter.extensions.autodetection.enabled", true)
            addSystemProperty("shared.indexes.download.auto.consent", true)

            // UI testing specific
            addSystemProperty("expose.ui.hierarchy.url", true) // Enable UI hierarchy inspection
            addSystemProperty("ide.experimental.ui", true) // Use new UI for testing

            // === OS-specific system properties ===

            when {
                SystemInfo.isMac -> {
                    // macOS specific settings
                    addSystemProperty("ide.mac.file.chooser.native", false) // Use Java file chooser
                    addSystemProperty("ide.mac.message.dialogs.as.sheets", false) // Use regular dialogs
                    addSystemProperty("jbScreenMenuBar.enabled", false) // Disable native menu bar
                    addSystemProperty("ide.native.launcher", true) // Use native launcher
                }

                SystemInfo.isWindows -> {
                    // Windows specific settings

                }

                SystemInfo.isLinux -> {
                    // Linux specific settings
                    addSystemProperty("ide.browser.jcef.enabled", true)
                    addSystemProperty("ide.native.launcher", false) // Avoid launcher issues on Linux

                    // X11/Wayland compatibility
                    addSystemProperty("sun.java2d.uiScale.enabled", false)
                    addSystemProperty("sun.java2d.xrender", false)
                }
            }
        }.addProjectToTrustedLocations().runIdeWithDriver().useDriverAndCloseIde {
            waitForIndicators(180.seconds)
        }
    }
}