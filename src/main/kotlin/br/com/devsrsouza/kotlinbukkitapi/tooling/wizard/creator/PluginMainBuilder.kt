package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.creator

import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.KBAPIModuleConfig
import java.io.File

fun generatePluginPackageFolder(
        root: File,
        config: KBAPIModuleConfig
): File = File(root, "src/main/kotlin/${config.packageName.replace(".", "/")}")

fun generatePluginMainFile(
        root: File,
        config: KBAPIModuleConfig
): File = File(generatePluginPackageFolder(root, config), "${config.pluginMainName}.kt")

fun generatePluginMainFileContent(
        config: KBAPIModuleConfig
): String = with(config) {
    return """
        package $packageName
        
        import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
        
        class $pluginMainName : KotlinPlugin() {
            override fun onPluginEnable() {
                
            }
            
            override fun onPluginDisble() {
            
            }
        }
    """.trimIndent()
}