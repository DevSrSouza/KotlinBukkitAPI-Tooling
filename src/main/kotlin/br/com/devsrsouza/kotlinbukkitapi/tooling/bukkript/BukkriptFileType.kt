package br.com.devsrsouza.kotlinbukkitapi.tooling.bukkript

import br.com.devsrsouza.kotlinbukkitapi.tooling.Assets
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.fileTypes.ex.FileTypeIdentifiableByVirtualFile
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.kotlin.idea.KotlinLanguage
import javax.swing.Icon

class BukkriptFileType : LanguageFileType(KotlinLanguage.INSTANCE), FileTypeIdentifiableByVirtualFile {

    companion object {
        @JvmField
        val INSTANCE = BukkriptFileType()
    }

    override fun getIcon(): Icon? = Assets.KotlinBukkitAPIIcon

    override fun getName(): String = BUKKRIPT_FILE

    override fun getDefaultExtension(): String = BUKKRIPT_EXTENSION

    override fun getDescription(): String = BUKKRIPT_DESCRIPTION

    override fun isMyFileType(file: VirtualFile): Boolean = file.name.isBukkriptScript()
}