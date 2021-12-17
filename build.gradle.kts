plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    kotlin("jvm") version "1.4.0"
}

val kotlinVersion = "1.4.0"
val kotlinBukkitAPIVersion = "0.2.0-SNAPSHOT"
val bukkriptVersion = "0.2.0-SNAPSHOT"

group = "br.com.devsrsouza.kotlinbukkitapi"
version = "0.0.7"

repositories {
    jcenter()
    mavenLocal()
    maven("http://nexus.devsrsouza.com.br/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    val changing = Action<ExternalModuleDependency> { isChanging = true }
    api("br.com.devsrsouza.bukkript:script-definition-embedded:$bukkriptVersion", changing)

    testCompile("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2020.2.1"

    setPlugins("java", "Kotlin")
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
    publishPlugin {
        token(System.getenv("ORG_GRADLE_PROJECT_intellijPublishToken"))
    }
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    sinceBuild("201")
    untilBuild("213.*")

    pluginDescription("""
        <img src="https://github.com/DevSrSouza/KotlinBukkitAPI/raw/master/logo.png" width="417" height="161"/>
        
        <br />

        The KotlinBukkitAPI Tooling is plugin for IntelliJ that helps 
        <br />
        developers using <a href="https://github.com/DevSrSouza/KotlinBukkitAPI">KotlinBukkitAPI</a> and scripts for <a href="https://github.com/DevSrSouza/Bukkript">Bukkript</a>.
        <br />
        This libraries help build extensions for Minecraft Server using Spigot server.
        
        <br />
        
        <br />
        
        <ul>
        <li><a href='https://github.com/DevSrSouza/Bukkript'>Bukkript</a></li>
        <li<a href='https://github.com/DevSrSouza/KotlinBukkitAPI-Tooling'>KotlinBukkitAPI-Tooling (this plugin)</a></li>
        <li<a href='https://github.com/DevSrSouza/KotlinBukkitAPI'>KotlinBukkitAPI</a></li>
        <li><a href='https://github.com/KotlinMinecraft/KotlinBukkitAPI-Examples'>KotlinBukkitAPI Examples</a></li>
        </ul>
        
        <br />
        <br />
        
        <h3>Demonstration</h3>
        
        <br />
        
        <img src="https://i.imgur.com/exlwVUs.gif" width='680' height='390'/>
    """.trimIndent())


    changeNotes("""
        <h3>0.0.6</h3>
        <br />
        
        <ul>
        <li>Update the embedded version of Bukkript and KotlinBukkitAPI.</li>
        <li>Adding KotlinBukkitAPI 0.2.0-SNAPSHOT to the Project Wizard.</li>
        <li>Improving overall performance of Menu Preview.</li>
        </ul>
        
        <h3>0.0.5</h3>
        <br />
        
        <ul>
        <li>Menu Preview support for 1.13+ Material enum.</li>
        <li>Increase Menu Preview performance</li>
        </ul>
        
        <br />
        
        <h3>0.0.3</h3>
        <br />
        
        <ul>
        <li>KotlinBukkitAPI Project Wizard</li>
        <li>Menu Preview tab only avaiable on projects that was KotlinBukkitAPI or is a Bukkript script.</li>
        <li>Custom folder icons for highlight the Bukkript related folders.</li>
        </ul>
    """.trimIndent())
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(120, "seconds")
}