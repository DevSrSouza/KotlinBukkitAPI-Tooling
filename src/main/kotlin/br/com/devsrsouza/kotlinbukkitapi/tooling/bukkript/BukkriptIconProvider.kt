package br.com.devsrsouza.kotlinbukkitapi.tooling.bukkript

import com.intellij.ide.IconProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon

class BukkriptIconProvider : IconProvider(), DumbAware {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        if(element is PsiFile && element.name.isBukkriptScript()) {
            return BukkriptFileType.INSTANCE.icon
        } else {
            return null
        }
    }
}