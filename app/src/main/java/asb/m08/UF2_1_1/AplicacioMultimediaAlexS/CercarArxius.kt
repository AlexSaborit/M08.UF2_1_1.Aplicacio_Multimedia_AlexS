package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Permanent

class CercarArxius : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cercar_arxius)

        val listView: ListView = findViewById(R.id.lvArxius)
        val tipusArxiu = intent.getStringExtra("TIPUS_ARXIU")

        if (tipusArxiu != null) {
            val arxius = llistarArxius(tipusArxiu)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arxius)
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->
                val arxiuSeleccionat = arxius[position]
                val intent = Intent()
                intent.putExtra("ARXIU_SELECCIONAT", arxiuSeleccionat)
                intent.putExtra("TIPUS_ARXIU", tipusArxiu)
                setResult(RESULT_OK, intent)
                finish()
            }
        } else {
            Toast.makeText(this, "No s'ha especificat el tipus d'arxiu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun llistarArxius(tipusArxiu: String): List<String> {
        val carpeta = when (tipusArxiu) {
            "imatge" -> Permanent.imgDir
            "so" -> Permanent.audioDir
            "video" -> Permanent.videoDir
            "text" -> Permanent.textDir
            else -> null
        }

        val arxius = mutableListOf<String>()
        carpeta?.listFiles()?.forEach { fitxer ->
            if (fitxer.isFile && fitxer.extension in getExtensions(tipusArxiu)) {
                arxius.add(fitxer.absolutePath)
            }
        }
        return arxius
    }

    private fun getExtensions(tipusArxiu: String): List<String> {
        return when (tipusArxiu) {
            "imatge" -> listOf("jpg", "jpeg", "png")
            "so" -> listOf("mp3", "wav", "aac")
            "video" -> listOf("mp4", "mkv", "avi")
            "text" -> listOf("txt")
            else -> emptyList()
        }
    }
}
