package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.utils

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/**
 * https://github.com/ktorio/ktor-init-tools/blob/master/ktor-intellij-plugin/src/io/ktor/start/intellij/util/Utils.kt
 */

operator fun VirtualFile?.get(path: String?): VirtualFile? {
    if (this == null || path == null || path == "" || path == ".") return this
    val parts = path.split('/', limit = 2)
    val firstName = parts[0]
    val lastName = parts.getOrNull(1)
    val child = this.findChild(firstName)
    return if (lastName != null) child[lastName] else child
}


fun Project.backgroundTask(
        name: String,
        indeterminate: Boolean = true,
        cancellable: Boolean = false,
        background: Boolean = false,
        callback: (indicator: ProgressIndicator) -> Unit
) {
    ProgressManager.getInstance().run(object : Task.Backgroundable(this, name, cancellable, { background }) {
        override fun shouldStartInBackground() = background

        override fun run(indicator: ProgressIndicator) {
            try {
                if (indeterminate) indicator.isIndeterminate = true
                callback(indicator)
            } catch (e: Throwable) {
                e.printStackTrace()
                throw e
            }
        }
    })
}

inline fun invokeLater(crossinline func: () -> Unit) {
    if (ApplicationManager.getApplication().isDispatchThread) {
        func()
    } else {
        ApplicationManager.getApplication().invokeLater({ func() }, ModalityState.defaultModalityState())
    }
}

