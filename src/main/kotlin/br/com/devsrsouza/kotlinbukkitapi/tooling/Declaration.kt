package br.com.devsrsouza.kotlinbukkitapi.tooling

data class MenuDeclaration(
    val displayname: String,
    val lines: Int,
    val slots: List<MenuSlotDeclaration>
)

data class MenuSlotDeclaration(
    val line: Int,
    val slot: Int,
    val itemId: Int?,
    val isSelected: Boolean = false
)
