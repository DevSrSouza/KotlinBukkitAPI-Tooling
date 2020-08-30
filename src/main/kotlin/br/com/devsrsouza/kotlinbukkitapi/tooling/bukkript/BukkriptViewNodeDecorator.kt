package br.com.devsrsouza.kotlinbukkitapi.tooling.bukkript

import br.com.devsrsouza.kotlinbukkitapi.tooling.Assets
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.packageDependencies.ui.PackageDependenciesNode
import com.intellij.ui.ColoredTreeCellRenderer

class BukkriptViewNodeDecorator : ProjectViewNodeDecorator {
    override fun decorate(node: ProjectViewNode<*>, data: PresentationData) {
        val file = node.virtualFile ?: return

        println(file.name)

        val icon = when {
            file.isScriptFolder() -> Assets.FolderScript
            file.isPluginsFolder() -> Assets.FolderPlugins
            file.isBukkriptFolder() -> Assets.FolderBukkript
            else -> null
        }

        println(icon)

        if(icon != null) {
            node.icon = icon
            data.setIcon(icon)
        }
    }

    override fun decorate(node: PackageDependenciesNode, cellRenderer: ColoredTreeCellRenderer) {

    }
}