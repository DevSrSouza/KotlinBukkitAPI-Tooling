package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit

data class Plugin(
        val name: String,
        val versions: List<PluginVersion>,
        val repositories: List<String>,
        val dependencies: List<String>
) {
    var isSelected = false
}