package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard

import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit.Plugin
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit.ServerType
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit.ServerVersion

data class KBAPIModuleConfig(
        var artifactGroup: String = "com.example",
        var artifactId: String = "MyPlugin",
        var artifactVersion: String = "0.0.1",
        var externalPlugins: List<Plugin> = listOf<Plugin>(),
        var kbAPIVersion: KotlinBukkitAPIVersion = Versions.LAST,
        var serverVersion: ServerVersion = ServerVersion.v1_8,
        var serverType: ServerType = ServerType.PAPERMC,
        var author: String = "",
        var website: String = "",
        var description: String = ""
)
