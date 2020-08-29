package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard

import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.utils.addAtGrid
import com.intellij.ide.util.projectWizard.*
import com.intellij.uiDesigner.core.*
import javax.swing.*

/**
 * https://github.com/ktorio/ktor-init-tools/blob/master/ktor-intellij-plugin/src/io/ktor/start/intellij/KtorArtifactWizardStep.kt
 */
class KBAPIArtifactWizard(val config: KBAPIModuleConfig) : ModuleWizardStep() {
    val groupId: JTextField
    val artifactId: JTextField
    val version: JTextField
    val author: JTextField
    val website: JTextField
    val description: JTextField

    val panel = JPanel().apply {
        layout = GridLayoutManager(8, 2)

        fun addLabelText(name: String, value: String, row: Int): JTextField {
            val tfield = JTextField(value)
            addAtGrid(
                    JLabel(name),
                    row = row,
                    column = 0,
                    anchor = GridConstraints.ANCHOR_WEST,
                    fill = GridConstraints.FILL_NONE,
                    HSizePolicy = GridConstraints.SIZEPOLICY_FIXED,
                    VSizePolicy = GridConstraints.SIZEPOLICY_FIXED
            )
            addAtGrid(
                    tfield,
                    row = row,
                    column = 1,
                    anchor = GridConstraints.ANCHOR_WEST,
                    fill = GridConstraints.FILL_HORIZONTAL,
                    HSizePolicy = GridConstraints.SIZEPOLICY_CAN_GROW or GridConstraints.SIZEPOLICY_WANT_GROW,
                    VSizePolicy = GridConstraints.SIZEPOLICY_FIXED
            )
            return tfield
        }

        addAtGrid(
                JLabel(""),
                row = 0,
                column = 0,
                HSizePolicy = GridConstraints.SIZEPOLICY_FIXED,
                VSizePolicy = GridConstraints.SIZEPOLICY_FIXED
        )

        groupId = addLabelText("GroupId", config.artifactGroup, row = 1)
        artifactId = addLabelText("Plugin Name", config.artifactId, row = 2)
        version = addLabelText("Version", config.artifactVersion, row = 3)
        author = addLabelText("Author", config.author, row = 4)
        website = addLabelText("Website", config.author, row = 5)
        description = addLabelText("Description", config.description, row = 6)

        add(Spacer().apply {}, GridConstraints().apply {
            colSpan = 2
            row = 7
            column = 0
            fill = GridConstraints.FILL_VERTICAL
        })
    }

    override fun updateDataModel() {
        config.artifactGroup = groupId.text
        config.artifactId = artifactId.text
        config.artifactVersion = version.text
        config.author = author.text
        config.website = website.text
        config.description = description.text
    }

    override fun getComponent() = panel
}
