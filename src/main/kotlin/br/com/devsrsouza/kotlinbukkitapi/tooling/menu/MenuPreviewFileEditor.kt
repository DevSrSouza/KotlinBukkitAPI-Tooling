package br.com.devsrsouza.kotlinbukkitapi.tooling.menu

import com.intellij.openapi.editor.event.CaretEvent
import com.intellij.openapi.editor.event.CaretListener
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.util.forEachDescendantOfType
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.layout.panel
import com.intellij.util.Alarm
import org.jetbrains.kotlin.psi.KtCallExpression
import java.awt.*
import java.beans.PropertyChangeListener
import javax.swing.*

class MenuPreviewFileEditor(
    private val myProject: Project,
    private val myFile: VirtualFile,
    private val mainEditor: TextEditor
) : UserDataHolderBase(), FileEditor {

    companion object {
        const val REBUILD_DELAY_MILLISECONDS = 80
    }

    private val mySwingAlarm = Alarm(Alarm.ThreadToUse.SWING_THREAD, this)
    private val myDocument = FileDocumentManager.getInstance().getDocument(myFile)!!
    private val myUi = JPanel().apply {
        layout = BorderLayout()
        isVisible = true
        minimumSize = Dimension(
            InventoryComponent.CHEST_WIDTH * InventoryComponent.CHEST_SCALE + 10,
            0
        )
    }

    private lateinit var currentScroll: JBScrollPane
    private var currentSelectedLine: Int? = null

    private val documentListener = object : DocumentListener {
        override fun beforeDocumentChange(event: DocumentEvent) {
            mySwingAlarm.cancelAllRequests()
        }
        override fun documentChanged(event: DocumentEvent) {
            mySwingAlarm.addRequest({
                rebuild(currentScroll.viewport.viewPosition)
            }, REBUILD_DELAY_MILLISECONDS)
        }
    }

    private val caretListener = object : CaretListener {
        override fun caretPositionChanged(event: CaretEvent) {
            currentSelectedLine = event.newPosition.line
            mySwingAlarm.addRequest({
                rebuild(currentScroll.viewport.viewPosition)
            }, REBUILD_DELAY_MILLISECONDS)
        }
    }

    fun setup() {
        rebuild(null)

        // listen to the source code changes to rebuild the UI
        myDocument.addDocumentListener(documentListener)

        // listen to cursor changes to make a slot selectable
        mainEditor.editor.caretModel.addCaretListener(caretListener)
    }

    fun rebuild(currentViewPosition: Point?) {
        if(!myUi.isShowing) return

        val tree = PsiDocumentManager.getInstance(myProject).getPsiFile(myDocument) ?: return

        // clear the old UI
        myUi.removeAll()

        // create the scroll pane with a centralized component with the inventories
        var menuCount = 0
        currentScroll = JBScrollPane(
            panel {
                tree.forEachDescendantOfType<KtCallExpression> {
                    val declaration =
                        findMenuDeclaration(
                            it,
                            currentSelectedLine
                        )
                    if (declaration != null) {
                        row {
                            component(
                                    InventoryComponent(
                                            declaration
                                    )
                            )
                        }
                        menuCount++
                    }
                }
            }.centerComponent(),
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        ).apply {
            if(currentViewPosition != null)
                viewport.viewPosition = currentViewPosition
        }

        if(menuCount > 0) {
            // add the scroll pane to the UI
            myUi.add(
                currentScroll,
                BorderLayout.CENTER
            )
        } else {
            myUi.add(
                JLabel("Currently this source code has no menu declaration!")
                    .centerComponent(),
                BorderLayout.CENTER
            )
        }
    }


    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = true

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {

    }

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
    }

    override fun getName(): String = "KotlinBukkitAPI Menu Viewer"

    override fun setState(state: FileEditorState) {

    }

    override fun getComponent(): JComponent {
        return myUi
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return null
    }

    override fun getCurrentLocation(): FileEditorLocation? {
        return null
    }

    override fun selectNotify() {
        setup()
    }

    override fun deselectNotify() {
        dispose()
    }

    override fun dispose() {
        mySwingAlarm.cancelAllRequests()
        myDocument.removeDocumentListener(documentListener)
        mainEditor.editor.caretModel.removeCaretListener(caretListener)
    }

    private fun JComponent.centerComponent(): JPanel {
        return JPanel().apply {
            isVisible = true
            layout = GridBagLayout()

            add(this@centerComponent)
        }
    }
}
