package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit

import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit.ServerVersion.*

private val worldEdit = "com.sk89q.worldedit"

// TODO: plotsquared, multiverse

val ALL_PLUGINS = listOf(
        Plugin(
                "Vault",
                allVersion("1.7") { setOf("com.github.MilkBowl:VaultAPI:$it") },
                setOf("https://jitpack.io"),
        ),
        Plugin(
                "WorldEdit",
                listOf(v1_8, v1_9, v1_12).asPluginVersion("6.1.5") { setOf("$worldEdit:worldedit-core:$it", "$worldEdit:worldedit-bukkit:$it") }
                        + listOf(v1_13, v1_14, v1_15, v1_16).asPluginVersion("7.2.0-SNAPSHOT") { setOf("$worldEdit:worldedit-core:$it", "$worldEdit:worldedit-bukkit:$it") }
                ,
                setOf("http://maven.sk89q.com/repo/")
        ),
        Plugin(
                "WorldGuard",
                listOf(v1_8, v1_9, v1_12).asPluginVersion("6.1") { setOf("com.sk89q:worldguard:$it") }
                        + listOf(v1_13, v1_14, v1_15, v1_16).asPluginVersion("7.0.4-SNAPSHOT") { setOf("com.sk89q.worldguard:worldguard-core:$it", "com.sk89q.worldguard:worldguard-bukkit:$it") }
                ,
                setOf("http://maven.sk89q.com/repo/")
        ),
        Plugin(
                "Citizens",
                allVersion("2.0.27-SNAPSHOT") { setOf("net.citizensnpcs:citizensapi:$it", "net.citizensnpcs:citizens:$it") },
                setOf("http://repo.citizensnpcs.co/")
        ),
        Plugin(
                "LuckPerms",
                allVersion("5.1") { setOf("net.luckperms:api:$it") },
                setOf() // mavenCentral
        ),
        Plugin(
                "ProtocolLib",
                allVersion("4.5.1") { setOf("com.comphenix.protocol:ProtocolLib:$it") },
                setOf("http://repo.dmulloy2.net/nexus/repository/public/")
        ),
        Plugin(
                "PlaceholderAPI",
                allVersion("2.9.2") { setOf("me.clip:placeholderapi:$it") },
                setOf("http://repo.extendedclip.com/content/repositories/placeholderapi/")
        ),
        Plugin(
                "MVdWPlaceholderAPI",
                allVersion("3.1.1") { setOf("be.maximvdw:MVdWPlaceholderAPI:$it") },
                setOf("http://repo.mvdw-software.be/content/groups/public/")),
        Plugin(
                "ActionBarAPI",
                allVersion("d60c2aedb9") { setOf("com.github.ConnorLinfoot:ActionBarAPI:$it") },
                setOf("https://jitpack.io")
        ),
        Plugin(
                "TitleAPI",
                allVersion("1.7.6") { setOf("com.github.ConnorLinfoot:TitleAPI:$it") },
                setOf("https://jitpack.io")),
        Plugin(
                "ViaVersion",
                allVersion("3.1.0") { setOf("us.myles:viaversion-common:$it") },
                setOf("https://repo.viaversion.com/")
        ),
        Plugin(
                "BossBarAPI",
                allVersion("2.4.2") { setOf("org.inventivetalent:bossbarapi:$it") },
                setOf("https://repo.inventivetalent.org/content/groups/public/")
        ),
        Plugin(
                "HologramAPI",
                allVersion("1.6.0") { setOf("org.inventivetalent:hologramapi:$it") },
                setOf("https://repo.inventivetalent.org/content/groups/public/")
        ),
)

inline fun allVersion(version: String, dependencies: (String) -> Set<String>): List<PluginVersion> = ServerVersion.values().map { PluginVersion(version, it, dependencies(version)) }
inline fun List<ServerVersion>.asPluginVersion(version: String, dependencies: (String) -> Set<String>): List<PluginVersion> = map { PluginVersion(version, it, dependencies(version)) }