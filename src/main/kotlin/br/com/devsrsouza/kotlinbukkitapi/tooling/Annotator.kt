package br.com.devsrsouza.kotlinbukkitapi.tooling

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditorWithPreview
import com.intellij.openapi.wm.IdeFocusManager
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtCallExpression
import javax.swing.SwingUtilities


/*
* This annotator if the file has menu declaration, he set the file editor
* to the KotlinBukkitAPI one.
*
object TestAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if(element is KtCallExpression && isMenuCall(element)) {
            val project = element.project
            val fileEditorManager = FileEditorManager.getInstance(project)

            SwingUtilities.invokeLater {
                val virtualFile = element.containingFile.virtualFile

                if(fileEditorManager.selectedEditor is TextEditorWithPreview) return@invokeLater

                fileEditorManager.setSelectedEditor(
                    virtualFile,
                    MenuPreviewFileEditorProvider.ID
                )
            }
        }
    }
}*/
