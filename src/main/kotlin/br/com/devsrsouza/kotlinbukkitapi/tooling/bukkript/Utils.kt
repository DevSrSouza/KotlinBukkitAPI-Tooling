package br.com.devsrsouza.kotlinbukkitapi.tooling.bukkript

fun String.isBukkriptScript() = endsWith(".$BUKKRIPT_EXTENSION", true)