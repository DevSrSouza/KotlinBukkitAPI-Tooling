@file:DependsOn("io.ktor:ktor-client-core:1.4.0")
@file:DependsOn("io.ktor:ktor-client-core-jvm:1.4.0")
@file:DependsOn("io.ktor:ktor-client-apache:1.4.0")
@file:DependsOn("io.ktor:ktor-client-json-jvm:1.4.0")
@file:DependsOn("io.ktor:ktor-client-gson:1.4.0")

@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

// HTML
@file:DependsOn("it.skrape:skrapeit-core:1.0.0-alpha6")
@file:DependsOn("io.github.rybalkinsd:kohttp:0.12.0")
@file:DependsOn("org.jsoup:jsoup:1.13.1")

// IMAGE RESIZE
@file:DependsOn("com.sksamuel.scrimage:scrimage-core:4.0.6")
@file:DependsOn("com.sksamuel.scrimage:scrimage-formats-extra:4.0.6")
@file:DependsOn("ar.com.hjg:pngj:2.1.0")
@file:DependsOn("com.github.zh79325:open-gif:1.0.4")

// CODE GENERATIOn
@file:DependsOn("com.squareup:kotlinpoet:1.6.0")

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.ScaleMethod
import com.sksamuel.scrimage.nio.PngWriter
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import it.skrape.core.htmlDocument
import it.skrape.extract
import it.skrape.skrape
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

data class Item(
        val id: String,
        val image: String,
)

var items = skrape {
    url = "https://www.deadmap.com/idlist"

    extract {
        htmlDocument {
            findFirst("#idlist").findAll("tr") {
                map {
                    val img = it.findFirst("img")
                            .attribute("src")
                            .replace(" ", "%20")

                    val id = it.findFirst("i").text

                    Item(id.removePrefix("minecraft:"), "https://www.deadmap.com/$img")
                }
            }
        }
    }
}

println(items.joinToString("\n"))

// FIXERS
val fixers = mapOf(
        "staned" to "stained",
        "dandelion_yellow" to "yellow_dye"
)

items = items.map { it.copy(fixers.entries.fold(it.id) { id, entry -> id.replace(entry.key, entry.value) }) }

runBlocking {
    val client = HttpClient(Apache)

    val iconsFolder = File("../src/main/resources/assets/items-13").canonicalFile
    iconsFolder.mkdirs()

    items
            .withIndex()
            .asFlow()
            .onEach { (index, item) ->
                val (name, imageUrl) = item
                println("Downloading item: $name ($index/${items.size - 1})")

                val imageBytes = client.get<ByteArray>(imageUrl)

                val image = ImmutableImage.loader()
                        .type(BufferedImage.TYPE_INT_ARGB)
                        .fromBytes(imageBytes)
                        .run {
                            val tags = metadata.tags()
                            val width = tags.first { it.name.contains("Width") }.rawValue.toInt()
                            val height = tags.first { it.name.contains("Height") }.rawValue.toInt()

                            if(width > 32 || height > 32) {
                                scaleTo(32, 32, ScaleMethod.FastScale)
                            } else this
                        }

                image.output(PngWriter.NoCompression, File(iconsFolder, "$name.png"))
                println("Complete download and resize: $name ($index/${items.size - 1})")
            }
            .collect()
}

println("Generating MinecrateItem13 Enumeration")

FileSpec.builder("br.com.devsrsouza.kotlinbukkitapi.tooling.menu", "MinecraftItem13")
        .addType(
                TypeSpec.enumBuilder("MinecraftItem13")
                        .apply {
                            for ((id, _) in items) {
                                addEnumConstant(id.toUpperCase())
                            }
                        }
                        .build()
        )
        .build()
        .writeTo(File("../src/main/kotlin/").canonicalFile)