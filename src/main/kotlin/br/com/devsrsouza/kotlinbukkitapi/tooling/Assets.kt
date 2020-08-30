package br.com.devsrsouza.kotlinbukkitapi.tooling

import com.intellij.ui.IconManager
import javax.swing.Icon

object Assets {
    private fun load(path: String): Icon {
        return IconManager.getInstance().getIcon(path, Assets::class.java)
    }

    private const val assetFolder = "/assets"

    val KotlinBukkitAPIIcon = load("$assetFolder/icons/kotlinbukkitapi-icon.svg")
    val Bukkript = load("$assetFolder/icons/bukkript.svg")
    val FolderScript = load("$assetFolder/icons/folderScript.svg")
    val FolderPlugins = load("$assetFolder/icons/folderPlugins.svg")
    val FolderBukkript = load("$assetFolder/icons/folderBukkript.svg")
}