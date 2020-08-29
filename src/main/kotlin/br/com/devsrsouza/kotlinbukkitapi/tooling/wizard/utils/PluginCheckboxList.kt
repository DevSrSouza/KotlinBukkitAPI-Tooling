package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.utils

import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.bukkit.Plugin
import com.intellij.ui.CheckboxTree
import com.intellij.ui.CheckedTreeNode
import com.intellij.ui.SimpleTextAttributes
import com.intellij.util.ui.ThreeStateCheckBox
import com.intellij.util.ui.UIUtil
import javax.swing.JTree
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath

open class ThreeStateCheckedTreeNode : CheckedTreeNode {
    constructor() : super()
    constructor(userObject: Any?) : super(userObject)

    var indeterminate = false
}

/**
 * Based on Ktor FeatureCheckboxList
 * https://github.com/ktorio/ktor-init-tools/blob/master/ktor-intellij-plugin/src/io/ktor/start/intellij/KtorModuleWizardStep.kt#L175
 */
abstract class PluginCheckboxList(val plugins: List<Plugin>) : CheckboxTree(
        object : CheckboxTree.CheckboxTreeCellRenderer() {
            override fun customizeRenderer(
                    tree: JTree?,
                    value: Any?,
                    selected: Boolean,
                    expanded: Boolean,
                    leaf: Boolean,
                    row: Int,
                    hasFocus: Boolean
            ) {
                if (value is ThreeStateCheckedTreeNode) {
                    val plugin = value.userObject
                    val tscheckbox = checkbox as ThreeStateCheckBox
                    if (plugin is Plugin) {
                        val style: SimpleTextAttributes = when {
                            value.indeterminate -> SimpleTextAttributes.REGULAR_ITALIC_ATTRIBUTES
                            else -> SimpleTextAttributes.REGULAR_ATTRIBUTES
                        }
                        textRenderer.append(plugin.name, style)
                        textRenderer.isEnabled = true
                        tscheckbox.isVisible = true
                        tscheckbox.state = when {
                            value.indeterminate -> ThreeStateCheckBox.State.DONT_CARE
                            value.isChecked -> ThreeStateCheckBox.State.SELECTED
                            else -> ThreeStateCheckBox.State.NOT_SELECTED
                        }
                        textRenderer.foreground = UIUtil.getTreeForeground()
                    } else if (plugin is String) {
                        textRenderer.append(plugin)
                        textRenderer.isEnabled = false
                        isEnabled = false
                        tscheckbox.isVisible = false
                    }
                }
            }
        },
        ThreeStateCheckedTreeNode()
) {
    val CheckedTreeNode?.plugin: Plugin? get() = this?.userObject as? Plugin?

    val pluginsToCheckbox = LinkedHashMap<Plugin, ThreeStateCheckedTreeNode>()
    val root = (this.model as DefaultTreeModel).root as ThreeStateCheckedTreeNode
    init {
        this.model = object : DefaultTreeModel(root) {
            override fun valueForPathChanged(path: TreePath, newValue: Any) {
                super.valueForPathChanged(path, newValue)
                val node = path.lastPathComponent as ThreeStateCheckedTreeNode
                val plugin = node.plugin
                if (plugin != null) {
                    onChanged(plugin, node)
                }
            }
        }
    }

    init {
        for (plugin in plugins) {
            root.add(ThreeStateCheckedTreeNode(plugin).apply { isChecked = false; pluginsToCheckbox[plugin] = this })
        }
        (this.model as DefaultTreeModel).reload(root)

        addTreeSelectionListener { e ->
            val node = (e.newLeadSelectionPath.lastPathComponent as? ThreeStateCheckedTreeNode)
            val plugin = node?.userObject as? Plugin?

            if (node != null) {
                onSelected(plugin, node)
            }
        }
    }

    abstract fun onSelected(feature: Plugin?, node: ThreeStateCheckedTreeNode)
    open fun onChanged(feature: Plugin, node: ThreeStateCheckedTreeNode) {
    }
}