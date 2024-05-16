package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Tria_Arxiu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tria_arxiu)

        val btnVisualitzarEditarText: Button = findViewById(R.id.btnVisualitzarEditarText)
        val btnMostrarImatge: Button = findViewById(R.id.btnMostrarImatge)
        val btnReproduirSo: Button = findViewById(R.id.btnReproduirSo)
        val ReproduirVideo: Button = findViewById(R.id.ReproduirVideo)

        btnVisualitzarEditarText.setOnClickListener{
            val intent = Intent(this, LlistatArxius::class.java)
            intent.putExtra("TIPUS_ARXIU", "text")
            startActivityForResult(intent, MainActivity.REQUEST_CODE_CERCAR_ARXIU)
        }
        btnMostrarImatge.setOnClickListener{
            val intent = Intent(this, LlistatArxius::class.java)
            intent.putExtra("TIPUS_ARXIU", "imatge")
            startActivityForResult(intent, MainActivity.REQUEST_CODE_CERCAR_ARXIU)
        }
        btnReproduirSo.setOnClickListener{
            val intent = Intent(this, LlistatArxius::class.java)
            intent.putExtra("TIPUS_ARXIU", "so")
            startActivityForResult(intent, MainActivity.REQUEST_CODE_CERCAR_ARXIU)
        }
        ReproduirVideo.setOnClickListener{
            val intent = Intent(this, LlistatArxius::class.java)
            intent.putExtra("TIPUS_ARXIU", "video")
            startActivityForResult(intent, MainActivity.REQUEST_CODE_CERCAR_ARXIU)
        }
    }

}