package br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.creator

import br.com.devsrsouza.kotlinbukkitapi.tooling.wizard.KBAPIModuleConfig
import java.io.File

const val DEFAULT_INDENTATION = "    "

inline fun String.mapIfNotBlank(block: (String) -> String): String =
        if(isNotBlank())
            block(this)
        else
            this

fun File.createIfNotExists(): File = apply {
    if(!exists()) {
        parentFile.mkdirs()
        createNewFile()
    }
}

val KBAPIModuleConfig.packageName get() = "${artifactGroup}.${artifactId.toLowerCase()}"
val KBAPIModuleConfig.pluginMainName get() = "${artifactId}Plugin"