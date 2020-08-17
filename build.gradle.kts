plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    kotlin("jvm") version "1.3.72"
}

group = "br.com.devsrsouza.kotlinbukkitapi"
version = "0.0.1-SNAPSHOT"

repositories {
    jcenter()
    maven("http://nexus.devsrsouza.com.br/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api("br.com.devsrsouza.bukkript:script-definition-embedded:0.1.0-SNAPSHOT")

    testCompile("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2020.1.2"

    setPlugins("java", "org.jetbrains.kotlin:1.3.72-release-IJ2020.1-3")
}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    pluginDescription("""
        The KotlinBukkitAPI Tooling adds Tooling to IntelliJ API when using <a href="https://github.com/DevSrSouza/KotlinBukkitAPI">KotlinBukkitAPI</a> and <a href="https://github.com/DevSrSouza/Bukkript">Bukkript</a>.
    """.trimIndent())
}

listOf("compileKotlin", "compileTestKotlin").forEach {
    tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>(it) {
        kotlinOptions.jvmTarget = "1.8"
    }
}