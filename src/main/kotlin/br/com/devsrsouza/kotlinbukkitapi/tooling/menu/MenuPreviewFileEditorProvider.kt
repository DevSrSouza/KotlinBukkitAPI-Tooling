package br.com.devsrsouza.kotlinbukkitapi.tooling.menu

import br.com.devsrsouza.kotlinbukkitapi.tooling.bukkript.BukkriptFileType
import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.kotlin.idea.KotlinFileType

class MenuPreviewFileEditorProvider : WeighedFileEditorProvider() {

    companion object {
        const val ID = "kotlinbukkitapi-menu-preview"
    }

    override fun getEditorTypeId(): String = ID

    override fun accept(project: Project, file: VirtualFile): Boolean {

        val type = file.fileType

        if(type == BukkriptFileType.INSTANCE) return true

        if(type != KotlinFileType.INSTANCE) return false

        val module = ModuleUtilCore.findModuleForFile(file, project) ?: return false

        val root = ModuleRootManager.getInstance(module)
        var hasKotlinBukkitAPI = false

        root.orderEntries().withoutSdk().forEachLibrary {
            if((it.name ?: "").contains("kotlinbukkitapi", true)) {
                hasKotlinBukkitAPI = true

                false
            } else {
                true
            }
        }

        return hasKotlinBukkitAPI
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        val editor: TextEditor = TextEditorProvider.getInstance().createEditor(project, file) as TextEditor
        val preview =
                MenuPreviewFileEditor(project, file, editor)

        return TextEditorWithPreview(editor, preview, "KotlinBukkitAPI")
    }

    //override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.HIDE_DEFAULT_EDITOR
    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR

}
