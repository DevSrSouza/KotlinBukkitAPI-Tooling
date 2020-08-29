package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard

import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.creator.*
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.utils.backgroundTask
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.utils.get
import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.utils.invokeLater
import com.intellij.codeInsight.actions.ReformatCodeProcessor
import com.intellij.ide.actions.ImportModuleAction
import com.intellij.ide.util.projectWizard.JavaModuleBuilder
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.project.DumbAwareRunnable
import com.intellij.openapi.projectRoots.JavaSdk
import com.intellij.openapi.projectRoots.SdkTypeId
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.Computable
import com.intellij.openapi.util.Ref
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.projectImport.ProjectImportProvider
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.Icon

class KBAPIModuleBuilder : JavaModuleBuilder() {
    override fun getPresentableName(): String = KBAPIModuleType.NAME
    override fun getNodeIcon(): Icon = KBAPIModuleType.ICON
    override fun getGroupName(): String = KBAPIModuleType.NAME
    override fun getBuilderId(): String? = "KOTLINBUKKITAPI_MODULE"
    override fun getWeight() = BUILD_SYSTEM_WEIGHT - 1
    override fun isSuitableSdkType(sdkType: SdkTypeId?): Boolean = sdkType === JavaSdk.getInstance()

    val config = KBAPIModuleConfig()

    override fun setupRootModel(rootModel: ModifiableRootModel) {
        val project = rootModel.project
        val (rootPath, vFile) = createAndGetRoot()
        val root = rootPath.toFile()
        rootModel.addContentEntry(vFile)
        if (moduleJdk != null) rootModel.sdk = moduleJdk

        project.backgroundTask("Setting Up Project") { progress ->
            progress.text = "Generating Gradle"

            val gradleBuildFile = generateGradleBuildFile(root).createIfNotExists()
            val gradleSettingFile = generateGradleSettingsFile(root).createIfNotExists()

            gradleBuildFile.appendText(generateGradleBuildFileContent(config))
            gradleSettingFile.appendText(generateGradleSettingsFileContent(config))

            progress.text = "Generating Plugin Main"

            val pluginMain = generatePluginMainFile(root, config).createIfNotExists()

            pluginMain.appendText(generatePluginMainFileContent(config))

            progress.text = "Configuring project gradle"
            vFile.refresh(false, true)

            val buildGradle = vFile["build.gradle.kts"]
            if (buildGradle != null)
                invokeLater {
                    val provider = ProjectImportProvider.PROJECT_IMPORT_PROVIDER.extensions
                            .firstOrNull { it.canImport(buildGradle, project) }
                            ?: return@invokeLater

                    val wizard = ImportModuleAction.createImportWizard(
                            project,
                            null,
                            buildGradle,
                            provider
                    )

                    if (wizard != null && (wizard.stepCount <= 0 || wizard.showAndGet())) {
                        ImportModuleAction.createFromWizard(project, wizard)
                    }
                }
        }
    }
    override fun getParentGroup() = KBAPIModuleType.NAME

    override fun createWizardSteps(wizardContext: WizardContext, modulesProvider: ModulesProvider) = arrayOf(
            KBAPIArtifactWizard(config)
    )

    override fun getCustomOptionsStep(context: WizardContext, parentDisposable: Disposable?) =
            KBAPIWizardStep(config)


    private fun createAndGetRoot(): Pair<Path, VirtualFile> {
        val temp = contentEntryPath ?: throw IllegalStateException("Failed to get content entry path")

        val pathName = FileUtil.toSystemIndependentName(temp)

        val path = Paths.get(pathName)
        Files.createDirectories(path)
        val vFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(pathName)
                ?: throw IllegalStateException("Failed to refresh and file file: $path")

        return path to vFile
    }

}

inline fun <T : Any?> runWriteTask(crossinline func: () -> T): T {
    return invokeAndWait {
        ApplicationManager.getApplication().runWriteAction(Computable { func() })
    }
}

fun <T : Any?> invokeAndWait(func: () -> T): T {
    val ref = Ref<T>()
    ApplicationManager.getApplication().invokeAndWait({ ref.set(func()) }, ModalityState.defaultModalityState())
    return ref.get()
}