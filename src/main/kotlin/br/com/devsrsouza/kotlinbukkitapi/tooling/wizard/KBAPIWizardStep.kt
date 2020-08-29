package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard

import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit.*
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.utils.*
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.ui.Splitter
import com.intellij.ui.IdeBorderFactory
import java.awt.BorderLayout
import javax.swing.*

/**
 * https://github.com/ktorio/ktor-init-tools/blob/master/ktor-intellij-plugin/src/io/ktor/start/intellij/KtorModuleWizardStep.kt
 */
class KBAPIWizardStep(
        val config: KBAPIModuleConfig
) : ModuleWizardStep() {
    lateinit var serverApiVersionCB: JComboBox<ServerVersion>
    lateinit var serverForkCB: JComboBox<ServerType>
    lateinit var kotlinBukkitAPIVersionCB: JComboBox<KotlinBukkitAPIVersion>
    lateinit var externalPluginList: PluginCheckboxList

    override fun updateDataModel() {
        config.serverVersion = serverApiVersionCB.selectedItem as ServerVersion
        config.externalPlugins = externalPluginList.pluginsToCheckbox.keys.filter { it.isSelected }
        config.serverType = serverForkCB.selectedItem as ServerType
        config.kbAPIVersion = kotlinBukkitAPIVersionCB.selectedItem as KotlinBukkitAPIVersion
    }

    val panel by lazy {
        JPanel().apply {
            val description = JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                border = IdeBorderFactory.createBorder()
            }

            externalPluginList = object : PluginCheckboxList(ALL_PLUGINS) {
                override fun onSelected(feature: Plugin?, node: ThreeStateCheckedTreeNode) {
                    // TODO: Show source link?
                }
                override fun onChanged(feature: Plugin, node: ThreeStateCheckedTreeNode) {
                    feature.isSelected = !feature.isSelected

                    externalPluginList.repaint()
                }
            }

            this.layout = BorderLayout(0, 0)

            add(table {
                tr(
                        policy = TdSize.FIXED,
                        fill = TdFill.NONE,
                        align = TdAlign.CENTER_LEFT
                ) {
                    td(JLabel("Server API Version:"))
                    td(JComboBox(ServerVersion.values()).apply { serverApiVersionCB = this })
                    td(JLabel("Server:"))
                    td(JComboBox(ServerType.values()).apply { serverForkCB = this })
                    td(JLabel("KotlinBukkitAPI:"))
                    td(JComboBox(Versions.ALL).apply {
                        kotlinBukkitAPIVersionCB = this
                        selectedItem = Versions.LAST
                    })
                }
            }, BorderLayout.NORTH)

            add(Splitter(true, 0.8f, 0.2f, 0.8f).apply {
                this.firstComponent = table {
                    tr(
                            policy = TdSize.FIXED,
                            minHeight = 24,
                            maxHeight = 24,
                            fill = TdFill.NONE,
                            align = TdAlign.CENTER_LEFT
                    ) {
                        td(JLabel("Plugins:"))
                    }
                    tr {
                        td(externalPluginList.scrollVertical())
                    }
                }
                this.secondComponent = description
            }, BorderLayout.CENTER)
        }
    }

    override fun getComponent(): JComponent {
        return panel
    }

}