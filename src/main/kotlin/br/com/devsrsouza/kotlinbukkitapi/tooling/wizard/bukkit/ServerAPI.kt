package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit

data class ServerAPI(
        val version: ServerVersion,
        val serverType: ServerType
)

val ServerAPI.dependencies: List<String> get() {

    val versionName = when(version) {
        ServerVersion.v1_8 -> "1.8.8-R0.1-SNAPSHOT"
        ServerVersion.v1_9 -> "1.9.4-R0.1-SNAPSHOT"
        ServerVersion.v1_12 -> "1.12.2-R0.1-SNAPSHOT"
        ServerVersion.v1_13 -> "1.13.2-R0.1-SNAPSHOT"
        ServerVersion.v1_14 -> "1.14.4-R0.1-SNAPSHOT"
        ServerVersion.v1_15 -> "1.15.2-R0.1-SNAPSHOT"
        ServerVersion.v1_16 -> "1.16.2-R0.1-SNAPSHOT"
    }

    val dependency = when(serverType) {
        ServerType.SPIGOT -> "org.spigotmc:spigot-api:$versionName"
        ServerType.PAPERMC -> {
            val v17 = "org.github.paperspigot:paperspigot-api"
            val v19 = "com.destroystokyo.paper:paper-api"

            when(version) {
                ServerVersion.v1_8 -> "$v17:$versionName"
                else -> "$v19:$versionName"
            }
        }
    }

    return listOf(dependency)
}

val ServerAPI.repositories: List<String> get() {
    return when(serverType) {
        ServerType.SPIGOT -> listOf(
                "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
                "https://oss.sonatype.org/content/repositories/snapshots/"
        )
        ServerType.PAPERMC -> listOf(
                "https://papermc.io/repo/repository/maven-public/",
                "https://oss.sonatype.org/content/repositories/snapshots/"
        )
    }
}