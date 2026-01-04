import org.gradle.kotlin.dsl.register
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java") // Java support
    alias(libs.plugins.kotlin) // Kotlin support
    alias(libs.plugins.intelliJPlatform) // IntelliJ Platform Gradle Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
    alias(libs.plugins.qodana) // Gradle Qodana Plugin
    alias(libs.plugins.kover) // Gradle Kover Plugin
    alias(libs.plugins.grammarkit) // Gradle Grammar-Kit Plugin
    idea // IntelliJ IDEA support
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

// Include the Grammar-Kit sources generated
sourceSets {
    main {
        java {
            srcDirs("src/main/gen")
        }
    }
    create("uiTest",Action<SourceSet>{
        compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
    })
}

idea {
    module {
        generatedSourceDirs.add(file("src/main/gen"))
        testSources.from(sourceSets.getByName("uiTest").kotlin.srcDirs)
        testResources.from(sourceSets.getByName("uiTest").resources.srcDirs)
    }
}

val uiTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

val uiTestRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.testRuntimeOnly.get())
}

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(21)
        @Suppress("UnstableApiUsage")
        vendor = JvmVendorSpec.JETBRAINS
    }
}

// Configure project's dependencies
repositories {
    mavenCentral()
    maven("https://cache-redirector.jetbrains.com/packages.jetbrains.team/maven/p/ij/intellij-ide-starter")

    // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
    intellijPlatform {
        defaultRepositories()
    }
}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/version_catalogs.html
dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.opentest4j)

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdea(providers.gradleProperty("platformVersion"))

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

        // Module Dependencies. Uses `platformBundledModules` property from the gradle.properties file for bundled IntelliJ Platform modules.
        bundledModules(providers.gradleProperty("platformBundledModules").map { it.split(',') })

        testFramework(TestFrameworkType.Platform)
        testBundledPlugin("org.jetbrains.kotlin")

        // IntelliJ UI Tests framework - read more: https://github.com/JetBrains/intellij-ide-starter
        testFramework(TestFrameworkType.Starter, configurationName = "uiTestImplementation")
        testFramework(TestFrameworkType.JUnit5, configurationName = "uiTestImplementation")
    }

    uiTestImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    uiTestImplementation("org.kodein.di:kodein-di-jvm:7.20.2")
    uiTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.1")
    uiTestRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        name = providers.gradleProperty("pluginName")
        version = providers.gradleProperty("pluginVersion")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }

        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
            with(changelog) {
                renderItem(
                    (getOrNull(pluginVersion) ?: getUnreleased())
                        .withHeader(false)
                        .withEmptySections(false),
                    Changelog.OutputType.HTML,
                )
            }
        }

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html#specifying-a-release-channel
        channels = providers.gradleProperty("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }

    caching.ides {
        enabled = true // global caching
        path = file(System.getProperty("user.home")).resolve(".ides")
        name = { "${it.type}-${it.version}" }
    }
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
    versionPrefix = ""
}

// Configure Gradle Kover Plugin - read more: https://kotlin.github.io/kotlinx-kover/gradle-plugin/#configuration-details
kover {
    reports {
        total {
            xml {
                onCheck = true
            }
        }
    }
}

tasks {
    generateParser {
        sourceFile.set(file("src/main/grammars/Sort.bnf"))
        pathToParser.set("com/github/jodiew/sortlines/lang/parser/SortParser.java")
        pathToPsiRoot.set("com/github/jodiew/sortlines/lang/psi")
        targetRootOutputDir.set(file("src/main/gen"))
        purgeOldFiles.set(true)
    }

    generateLexer {
        sourceFile.set(file("src/main/grammars/Sort.flex"))
        targetOutputDir.set(file("src/main/gen/com/github/jodiew/sortlines/lang"))
        purgeOldFiles.set(false)

        dependsOn(generateParser)
    }

    compileKotlin {
        dependsOn(generateLexer)
    }

    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }

    runIde {
        systemProperties(
            "ide.native.launcher" to true,
            "ide.show.tips.on.startup.default.value" to false,
            "jb.consents.confirmation.enabled" to false
        )
    }

    publishPlugin {
        dependsOn(patchChangelog)
    }

    register<Test>("uiTest") {
        description = "Runs only the UI tests that start the IDE"
        group = "verification"
        val uiTestSourceSet = sourceSets["uiTest"]
        testClassesDirs = uiTestSourceSet.output.classesDirs
        classpath = uiTestSourceSet.runtimeClasspath
        systemProperty("path.to.build.plugin", buildPlugin.get().archiveFile.get().asFile.absolutePath)
        systemProperty("idea.home.path", prepareTestSandbox.get().getDestinationDir().parentFile.absolutePath)
        systemProperty("platform.version", providers.gradleProperty("platformVersion").get())
        systemProperty(
            "allure.results.directory", project.layout.buildDirectory.get().asFile.absolutePath + "/allure-results"
        )
        useJUnitPlatform()

        // Add required JVM arguments
        jvmArgumentProviders += CommandLineArgumentProvider {
            mutableListOf(
                "--add-opens=java.base/java.lang=ALL-UNNAMED",
                "--add-opens=java.desktop/javax.swing=ALL-UNNAMED"
            )
        }

        dependsOn(buildPlugin)
    }
}
