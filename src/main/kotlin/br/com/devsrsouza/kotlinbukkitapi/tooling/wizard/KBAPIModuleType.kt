package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard

import br.com.devsrsouza.kotlinbukkitapi.tooling.Assets
import com.intellij.openapi.module.ModuleType
import javax.swing.Icon

class KBAPIModuleType : ModuleType<KBAPIModuleBuilder>("kotlinbukkitapi") {

    companion object {
        val NAME = "KotlinBukkitAPI"
        val DESCRIPTION = "KotlinBukkitAPI Quick"
        val ICON = Assets.KotlinBukkitAPIIcon
        val INSTANCE = KBAPIModuleType()
    }

    override fun createModuleBuilder(): KBAPIModuleBuilder = KBAPIModuleBuilder()
    override fun getName(): String = NAME
    override fun getDescription(): String = DESCRIPTION
    override fun getNodeIcon(isOpened: Boolean): Icon = ICON
    override fun getIcon(): Icon = ICON

}