package br.com.devsrsouza.kotlinbukkitapi.tooling


import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.kotlin.idea.KotlinFileType
class MenuPreviewFileEditorProvider : WeighedFileEditorProvider() {

    companion object {
        const val ID = "kotlinbukkitapi-menu-preview"
    }

    override fun getEditorTypeId(): String =
        ID

    override fun accept(project: Project, file: VirtualFile): Boolean {

        val type = file.fileType

        if(type != KotlinFileType.INSTANCE) return false

        return true
//        return PsiManager.getInstance(project).findFile(file)?.findDescendantOfType<KtCallExpression> {
//            findMenuDeclaration(it) != null
//        } != null
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
