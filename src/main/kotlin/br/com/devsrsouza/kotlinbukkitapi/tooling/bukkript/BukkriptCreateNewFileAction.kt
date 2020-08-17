package br.com.devsrsouza.kotlinbukkitapi.tooling.bukkript

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile

class BukkriptCreateNewFileAction : CreateFileFromTemplateAction(
    BUKKRIPT_SCRIPT,
    BUKKRIPT_DESCRIPTION,
    BukkriptFileType.INSTANCE.icon
) {
    override fun getActionName(
        directory: PsiDirectory?,
        newName: String,
        templateName: String?
    ): String = BUKKRIPT_CREATE_SCRIPT_DESCRIPTION

    override fun createFile(
        name: String,
        templateName: String,
        dir: PsiDirectory
    ): PsiFile? = super.createFile("$name.bk", templateName, dir)

    override fun buildDialog(
        project: Project,
        directory: PsiDirectory,
        builder: CreateFileFromTemplateDialog.Builder
    ) {
        builder.setTitle(BUKKRIPT_SCRIPT)
            .addKind(
                BUKKRIPT_FILE,
                BukkriptFileType.INSTANCE.icon,
                BUKKRIPT_SCRIPT
            )

    }
}