package br.com.devsrsouza.kotlinbukkitapi.tooling.bukkript

import br.com.devsrsouza.kotlinbukkitapi.tooling.Assets
import com.intellij.ide.IconProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon

class BukkriptIconProvider : IconProvider(), DumbAware {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        if(element is PsiFile) {
            val file = element.virtualFile
            return when {
                file.isBukkriptScript() -> BukkriptFileType.INSTANCE.icon
                file.isScriptFolder() -> Assets.FolderScript
                file.isPluginsFolder() -> Assets.FolderPlugins
                file.isBukkriptFolder() -> Assets.FolderBukkript
                else -> null
            }
        } else {
            return null
        }
    }
}