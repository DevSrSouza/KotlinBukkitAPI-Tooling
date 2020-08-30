package br.com.devsrsouza.kotlinbukkitapi.tooling.bukkript

import com.intellij.openapi.vfs.VirtualFile
import java.io.File

fun String.isBukkriptScript() = endsWith(".$BUKKRIPT_EXTENSION", true)

fun VirtualFile.isBukkriptScript() = name.isBukkriptScript()

fun VirtualFile.isScriptFolder(): Boolean {
    if(name != "scripts") return false

    val bukkriptFodler = parent ?: return false

    return bukkriptFodler.isBukkriptFolder()
}

fun VirtualFile.isPluginsFolder(): Boolean {
    if(name != "plugins") return false

    val rootServerFolder = parent ?: return false

    return rootServerFolder.children?.find { it.name == "server.properties" } != null
}

fun VirtualFile.isBukkriptFolder(): Boolean {
    if(name != "Bukkript") return false

    val pluginsFolder = parent ?: return false

    return pluginsFolder.name == "plugins"
}