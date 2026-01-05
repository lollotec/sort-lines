package com.github.jodiew.sortlines.ui

import com.intellij.driver.sdk.ui.components.common.welcomeScreen
import com.intellij.driver.sdk.ui.shouldBe
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.plugins.PluginConfigurator
import com.intellij.ide.starter.project.NoProject
import com.intellij.ide.starter.runner.Starter
import com.intellij.openapi.util.SystemInfo
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class PluginTest {

    /**
     * Test to verify that the plugin is installed in the IDE.
     */
    @Test
    fun simpleTest() {
        Starter.newContext("testInstalled", TestCase(IdeProductProvider.IU, NoProject).withVersion(System.getProperty("platform.version"))).apply {
            val pathToPlugin = System.getProperty("path.to.build.plugin")
            PluginConfigurator(this).installPluginFromPath(Path(pathToPlugin))
        }.applyVMOptionsPatch{
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
        }.runIdeWithDriver().useDriverAndCloseIde {
            welcomeScreen {
                clickPlugins()
                x { byAccessibleName("Installed") }.click()
                keyboard {
                    typeText("Sort Lines", 50)
                }
                shouldBe("Plugin is installed") {
                    x {
                            byVisibleText("Sort Lines with Comment")
                    }.present()
                }

            }
        }
    }
}