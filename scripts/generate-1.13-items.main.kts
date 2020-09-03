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

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.nio.PngReader
import com.sksamuel.scrimage.nio.PngWriter
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import it.skrape.core.htmlDocument
import it.skrape.extract
import it.skrape.skrape
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlinx.coroutines.flow.*

data class Item(
        val id: String,
        val image: String
)

val items = skrape {
    url = "https://www.deadmap.com/idlist"

    extract {
        htmlDocument {
            findFirst("#idlist").findAll("tr") {
                PngReader()

                map {
                    val img = it.findFirst("img")
                            .attribute("src")
                            .replace(" ", "%20")

                    val id = it.findFirst("i").text

                    Item(id, "https://www.deadmap.com/$img")
                }
            }
        }
    }
}

println(items.joinToString("\n"))

runBlocking {
    val client = HttpClient(Apache)

    val iconsFolder = File("../src/main/resources/assets/items-13").canonicalFile
    iconsFolder.mkdirs()

    items.withIndex()
            .asFlow()
            .onEach { (index, item) ->
                val (id, imageUrl) = item
                println("Downloading item: $id ($index/${items.size - 1})")
                val name = id.removePrefix("minecraft:")

                val imageBytes = client.get<ByteArray>(imageUrl)

                val image = ImmutableImage.loader()
                        .fromBytes(imageBytes)
                        .run {
                            val tags = metadata.tags()
                            val width = tags.first { it.name.contains("Width") }.rawValue.toInt()
                            val height = tags.first { it.name.contains("Height") }.rawValue.toInt()

                            if(width > 32 || height > 32)
                                scaleTo(32, 32)
                            else this
                        }

                image.output(PngWriter.NoCompression, File(iconsFolder, "$name.png"))
                println("Complete download and resize: $id ($index/${items.size - 1})")
            }
            .collect()
}