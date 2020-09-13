package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard

object Versions {
    val `v0_1_0-SNAPSHOT` = KotlinBukkitAPIVersion("0.1.0-SNAPSHOT")
    val `v0_2_0-SNAPSHOT` = KotlinBukkitAPIVersion("0.2.0-SNAPSHOT")

    val ALL = arrayOf(
        `v0_2_0-SNAPSHOT`,
        `v0_1_0-SNAPSHOT`
    )

    val LAST = `v0_2_0-SNAPSHOT`

    val visitorAll = ALL.associate { it.version to it }

    fun fromString(version: String) = visitorAll[version]
}

data class KotlinBukkitAPIVersion(
    val version: String,
    val kotlinVersion: String = "1.4.0"
) {
    override fun toString(): String {
        return version
    }

    private val group = "br.com.devsrsouza.kotlinbukkitapi"

    val repositories = listOf(
        "http://nexus.devsrsouza.com.br/repository/maven-public/",
        /*"https://oss.jfrog.org/oss-snapshot-local/", // TODO: create a option to provide this repositories optionally
        "http://nexus.okkero.com/repository/maven-releases/",
        "https://repo.codemc.org/repository/maven-public",*/
    )

    val dependencies = listOf(
        "${group}:core:$version",
        "${group}:serialization:$version",
        "${group}:plugins:$version",
        "${group}:exposed:$version",
    )
}