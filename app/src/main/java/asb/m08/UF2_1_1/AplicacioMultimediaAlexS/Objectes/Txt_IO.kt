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
    //data class Line(val text: String, val style: TextStyle, val size: TextSize)

    class TextFileHandler(val fileName: String) {
        // Funció per llegir l'arxiu de text
        public fun readLines(): MutableList<Text> {
            val textos = mutableListOf<Text>()
            File(fileName).forEachLine { line ->
                val parts = line.split(";")
                if (parts.size == 3) {
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
                    textos.add(Text(text, style, size))
                }
            }
            return textos
        }

        // Funció per escriure a l'arxiu de text
        fun writeLines(lines: MutableList<Text>) {
            val file = File(fileName)
            file.bufferedWriter().use { out ->
                lines.forEach { line ->
                    out.write("${line.text};${line.estil};${line.mida}\n")
                }
            }
        }
    }

    //funció que crea un arxiu de text
    fun test() {
        val fileName = "textfile.txt"
        val textHandler = TextFileHandler(fileName)

        // Escriu algunes línies a l'arxiu
        val linesToWrite = mutableListOf(
            Text("Text normal", TextStyle.NORMAL, TextSize.MITJANA),
            Text("Text en negreta petit", TextStyle.NEGRETA, TextSize.PETITA),
            Text("Text en cursiva gran", TextStyle.CURSIVA, TextSize.GRAN)
        )
        textHandler.writeLines(linesToWrite)

        // Llegeix les línies de l'arxiu i les imprimeix
        val linesRead = textHandler.readLines()
        linesRead.forEach { line ->
            println("${line.text} (${line.estil}, ${line.mida})")
        }
    }
}