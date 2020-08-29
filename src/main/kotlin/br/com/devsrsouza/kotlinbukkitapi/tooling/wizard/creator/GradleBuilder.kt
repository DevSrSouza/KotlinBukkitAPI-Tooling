package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.creator

import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.KBAPIModuleConfig
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.PLUGIN_YML
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.SHADOW_PLUGIN
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit.ServerAPI
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit.dependencies
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit.repositories
import org.jetbrains.kotlin.nj2k.replace
import java.io.File

fun generateGradleBuildFile(
        root: File
): File = File(root, "build.gradle.kts")

fun generateGradleSettingsFile(
        root: File
): File = File(root, "settings.gradle.kts")

fun generateGradleBuildFileContent(
        config: KBAPIModuleConfig
): String = with(config) {
    val kbapiDependencies = kbAPIVersion.dependencies
            .dependenciesAsGradle()

    val kbapiRepositories = kbAPIVersion.repositories
            .repositoriesAsGradle()

    val serverApi = ServerAPI(config.serverVersion, config.serverType)
    val serverRepositories = serverApi.repositories.repositoriesAsGradle()
    val serverDependencies = serverApi.dependencies.dependenciesAsGradle()

    val pluginDependencies = externalPlugins
            .flatMap { it.dependencies.map { dep -> dep.replace("{version}", it.versions.first { it.serverVersion == serverVersion }.version) } }
            .distinct()
            .dependenciesAsGradle()


    val pluginRepositories = externalPlugins
            .flatMap { it.repositories }
            .distinct()
            .repositoriesAsGradle()


    val plugins = externalPlugins.joinToString { "\"${it.name}\"" }

    return """
        |plugins {
        |    kotlin("jvm") version "${kbAPIVersion.kotlinVersion}"
        |    kotlin("plugin.serialization") version "${kbAPIVersion.kotlinVersion}"
        |    id("net.minecrell.plugin-yml.bukkit") version "$PLUGIN_YML"
        |    id("com.github.johnrengelman.shadow") version "$SHADOW_PLUGIN"
        |}
        |
        |group = "$artifactGroup"
        |version = "$artifactVersion"
        |
        |repositories {
        |    jcenter()
        |    // minecraft
        |$serverRepositories
        |
        |    //kotlinbukkitapi with backup repo
        |$kbapiRepositories
        |    
        |    //plugins
        |$pluginRepositories
        |}
        |
        |dependencies {
        |    //minecraft
        |$serverDependencies
        |
        |    //kotlinbukkitapi
        |$kbapiDependencies
        |
        |    //plugins
        |$pluginDependencies
        |}
        |
        |bukkit {
        |    main = "${packageName}.${pluginMainName}"
        |    depend = listOf("KotlinBukkitAPI", ${plugins})
        |    ${description.mapIfNotBlank { "description = \"$it\"" }}
        |    ${author.mapIfNotBlank { "author = \"$it\"" }}
        |    ${website.mapIfNotBlank { "website = \"$it\"" }}
        |}
        |
        |tasks {
        |    compileKotlin {
        |        kotlinOptions.jvmTarget = "1.8"
        |        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime,kotlin.ExperimentalStdlibApi"
        |    }
        |    compileTestKotlin {
        |        kotlinOptions.jvmTarget = "1.8"
        |        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime,kotlin.ExperimentalStdlibApi"
        |    }
        |    shadowJar {
        |        classifier = null
        |    }
        |}
        |
        |configurations.all {
        |    resolutionStrategy.cacheChangingModulesFor(120, "seconds")
        |}
    """.trimMargin()
}

private inline fun List<String>.dependenciesAsGradle(configuration: String = "compileOnly"): String =
        joinToString("\n") {
            "$configuration(\"${it}\")"
        }.prependIndent(DEFAULT_INDENTATION)

private inline fun List<String>.repositoriesAsGradle(): String =
        joinToString("\n") {
            "maven(\"${it}\")"
        }.prependIndent(DEFAULT_INDENTATION)

fun generateGradleSettingsFileContent(
        config: KBAPIModuleConfig
): String = with(config) {
    return """
        rootProject.name = "$artifactId"
    """.trimSource()
}

fun String.trimSource(): String {
    val lines = lines()
    val indentation = lines[1].indexOfFirst { !it.isWhitespace() }

    return lines.joinToString("\n") { it.removePrefix(" ".repeat(indentation)) }
}