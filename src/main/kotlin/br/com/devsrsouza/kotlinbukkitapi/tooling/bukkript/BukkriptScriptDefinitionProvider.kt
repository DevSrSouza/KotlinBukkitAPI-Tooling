package br.com.devsrsouza.kotlinbukkitapi.tooling.bukkript

import org.jetbrains.kotlin.utils.PathUtil
import java.io.File
import kotlin.script.experimental.intellij.ScriptDefinitionsProvider
import br.com.devsrsouza.bukkript.script.definition.BukkriptScript

class BukkriptScriptDefinitionProvider : ScriptDefinitionsProvider {

    companion object {
        const val ID = "BukkriptScriptDefinition"
    }

    override val id: String
        get() = ID

    override fun getDefinitionClasses(): Iterable<String> {
        return listOf("br.com.devsrsouza.bukkript.script.definition.BukkriptScript")
    }

    override fun getDefinitionsClassPath(): Iterable<File> {
        val jarFile = PathUtil.getResourcePathForClass(BukkriptScript::class.java)

        return listOf(jarFile)
    }

    override fun useDiscovery(): Boolean {
        return false
    }


}