plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    java
    kotlin("jvm") version "1.4.0"
}

val kotlinVersion = "1.4.0"
val kotlinBukkitAPIVersion = "0.1.0-SNAPSHOT"
val bukkriptVersion = "0.1.0-SNAPSHOT"

group = "br.com.devsrsouza.kotlinbukkitapi"
version = "0.0.2"

repositories {
    jcenter()
    //maven("http://nexus.devsrsouza.com.br/repository/maven-public/")
    mavenLocal()
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
    untilBuild("202.*")

    pluginDescription("""
        <img src="https://github.com/DevSrSouza/KotlinBukkitAPI/raw/master/logo.png" width="417" height="161"/>
        
        <br />

        The KotlinBukkitAPI Tooling is plugin for IntelliJ that helps developers using <a href="https://github.com/DevSrSouza/KotlinBukkitAPI">KotlinBukkitAPI</a> and scripts for <a href="https://github.com/DevSrSouza/Bukkript">Bukkript</a>. This libraries help build extensions for Minecraft Server using Spigot server.
        
        <br />

        <img src="https://img.shields.io/static/v1?label=Kotlin&message=${kotlinVersion}&color=Orange&style=for-the-badge" />
        <a href="https://github.com/DevSrSouza/KotlinBukkitAPI"><img src="https://img.shields.io/static/v1?label=KotlinBukkitAPI&message=${kotlinBukkitAPIVersion}&color=Orange&style=for-the-badge" /></a>
        <a href="https://github.com/DevSrSouza/Bukkript"><img src="https://img.shields.io/static/v1?label=Bukkript&message=${bukkriptVersion}&color=Orange&style=for-the-badge" /></a>
        <img src="https://img.shields.io/github/stars/DevSrSouza/KotlinBukkitAPI-Tooling.svg?style=for-the-badge&color=orange&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAADYklEQVR4XuWbvbPNQBiHn9uo6NFT0GEYHa0/QofOR2MojI8xxmh8VFwVf4RaxTB0KOhdPRUF8zPJmdy1J3l3N9ndxDb3zr0n2exzfu/zJjk5G5Qdh5vp35c6jI1SEzfzbgK/gXOljqMkgF3A1wbAXuBHCQglAehdf9wsWr8rDdlHSQCq+0MdBxzJvnqgFADJ752zYAHILsNSABT3Mw4A/S27DEsAaOW30wHwHcguwxIAuvJzyz67DEsA6MrPBaD/ZZVhbgA++bkQssowNwCf/FwAWWWYE8A6+bkAssowJ4A++RWTYU4AffIrJsNcACzyKyLDXAAs8isiwxwArPIrIsMcAELkl12GOQCEyC+7DAUgpj5LXLpPMeemAOwAXgLHp5ih4n2+Ak62JbCnuRmhn//D2ALUmre6DlAClAQlYsnjJ3ACeK1FuhI8CzxZ8uqbu06rG7C+LrBkKf5zpekDsFQp/pUeoBJYjXXnAUuT4kp6bnn3nQgtRYrbpBcCQK9dghR7b7RaToXnLMXB22sWAHOVold6oSXQvn5uUlwrvVgA2m4uUuyVXgqAuUgx6NMliwN8t6rcDzZrOXselF5qArR9rVI0SW8MANpHbVI0S28sADVJMUh6YwKoRYpB0hsbwEHgQ2ED6hg+xR5DTBfoznUDuB47+Ujb6Rhuxu4rFcBH4EDs5CNtp3dfKYgaKQBqiH+76OgySAFQQ/xbANFlkAKghvi3AKLLIBZATfFPKoNYADXFP6kMYgHUFP+kMogBUGP8o8sgBsBY8f8MXGqO/D6wP6qRb98ouBvEAEiNvx6DuwU8BH41x69L7AvANUBPlMSO4G4QCiAl/vpqzDPgKvBtzQp3A3eB0wmP8gedFIUCiI3/G+A88Nb41h4DHgFHja93r0/M1wahAELjr3f6CvC8+W5QyHp0bEqCEqFkWEdQGYQACIm/blI8AG4DqvmUISfIDXKE9dkFcxmEALDG/wVwEfiSsmrPtvsaqKcM+zV3gxAAQ/Fv25oATDkEYKhtmsvACqAv/r62NiUA7dvSNk1lYAXgi7+lrU0Noq9tmsrACsCNf2hbmxqEr22aysACoBv/lLY2NQRf2xwsAwsARUlnb2O1talBdNvmnaEbphYA94CnE7S1qUGobeozzMt9E/0BUX274HXoySkAAAAASUVORK5CYII=" />
        <img src="https://img.shields.io/badge/license-MIT-blue.svg?color=ORANGE&style=for-the-badge" />
        
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
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(120, "seconds")
}