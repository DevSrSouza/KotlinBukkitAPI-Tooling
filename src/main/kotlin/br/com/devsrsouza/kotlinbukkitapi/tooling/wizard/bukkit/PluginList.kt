package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit

val ALL_PLUGINS = listOf(
        Plugin(
                "Vault",
                ServerVersion.values().map { PluginVersion("1.7", it) },
                listOf("https://jitpack.io"),
                listOf("com.github.MilkBowl:VaultAPI:{version}")
        )
)