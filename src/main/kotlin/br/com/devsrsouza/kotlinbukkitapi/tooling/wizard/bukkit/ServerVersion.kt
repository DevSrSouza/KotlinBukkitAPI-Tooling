package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit

enum class ServerVersion(val displayName: String) {
    v1_8("1.8"),
    v1_9("1.9"),
    v1_12("1.12"),
    v1_13("1.13"),
    v1_14("1.14"),
    v1_15("1.15"),
    v1_16("1.16");

    override fun toString(): String {
        return displayName
    }
}
