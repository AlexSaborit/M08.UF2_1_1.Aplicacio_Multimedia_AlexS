package asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes

import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Classes.Text
import java.io.File

object Txt_IO {
    enum class TextStyle {
        NORMAL,
        NEGRETA,
        CURSIVA
    }

    enum class TextSize {
        PETITA,
        MITJANA,
        GRAN
    }

    class TextFileHandler {
        // Funció per escriure un objecte Text en un fitxer individual
        fun writeText(text: Text, fileName: String): Boolean {
            var escrit: Boolean = false
            try {
                val filePath = File(Permanent.textDir, fileName).absolutePath
                val file = File(filePath)
                if (!file.exists())
                {
                    file.bufferedWriter().use { out ->
                        out.write("${text.text};${text.estil};${text.mida}\n")
                    }
                    escrit = true
                } else {
                    escrit = false
                }

            } catch (e: Exception) {
            }
            return escrit
        }

        // Funció per llegir un fitxer de text i convertir-lo en un objecte Text
        fun readText(fileName: String): Text? {
            val filePath = File(Permanent.textDir, fileName).absolutePath
            val file = File(filePath)
            if (!file.exists()) return null

            val line = file.readText()
            val parts = line.split(";")
            return if (parts.size == 3) {
                val text = parts[0]
                val style = when (parts[1]) {
                    "NORMAL" -> TextStyle.NORMAL
                    "NEGRETA" -> TextStyle.NEGRETA
                    "CURSIVA" -> TextStyle.CURSIVA
                    else -> TextStyle.NORMAL
                }
                val size = when (parts[2]) {
                    "PETITA" -> TextSize.PETITA
                    "NORMAL" -> TextSize.MITJANA
                    "GRAN" -> TextSize.GRAN
                    else -> TextSize.MITJANA
                }
                Text(text, style, size)
            } else {
                null
            }
        }
    }

    // Funció que crea fitxers de text individuals
    fun test() {
        val textHandler = TextFileHandler()

        // Escriu alguns objectes Text en fitxers individuals
        val textsToWrite = listOf(
            Text("Text normal", TextStyle.NORMAL, TextSize.MITJANA),
            Text("Text en negreta petit", TextStyle.NEGRETA, TextSize.PETITA),
            Text("Text en cursiva gran", TextStyle.CURSIVA, TextSize.GRAN)
        )

        // Especifica el nom del fitxer per cada objecte Text
        val fileNames = listOf("textfile1.txt", "textfile2.txt", "textfile3.txt")

        textsToWrite.forEachIndexed { index, text ->
            textHandler.writeText(text, fileNames[index])
        }

        // Llegeix els fitxers i imprimeix el seu contingut
        fileNames.forEach { fileName ->
            val textRead = textHandler.readText(fileName)
            textRead?.let {
                println("${it.text} (${it.estil}, ${it.mida})")
            }
        }
    }
}
