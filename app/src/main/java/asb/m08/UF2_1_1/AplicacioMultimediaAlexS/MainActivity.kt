package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCrearTxt = findViewById<Button>(R.id.btnCrearTxt)
        val btnCapturarFoto = findViewById<Button>(R.id.btnCapturarFoto)
        val btnCapturarVideo = findViewById<Button>(R.id.btnCapturarVideo)
        val btnCapturarSo = findViewById<Button>(R.id.btnCapturarSo)
        val btnVisualitzarReproduir = findViewById<Button>(R.id.btnVisualitzarReproduir)
        val btnServeisOnline = findViewById<Button>(R.id.btnServeisOnline)

        btnCrearTxt.setOnClickListener {
            val intent = Intent(this, Text_Activity::class.java)
            startActivity(intent)
        }
        btnCapturarFoto.setOnClickListener {
            Toast.makeText(this, "Has premut el botó!", Toast.LENGTH_SHORT).show()
            //TODO(crida a funció per capturar foto)
        }
        btnCapturarVideo.setOnClickListener {
            Toast.makeText(this, "Has premut el botó!", Toast.LENGTH_SHORT).show()
            //TODO(crida a funció per capturar vídeo)
        }
        btnCapturarSo.setOnClickListener {
            Toast.makeText(this, "Has premut el botó!", Toast.LENGTH_SHORT).show()
            //TODO(crida a funció per capturar so)
        }
        btnVisualitzarReproduir.setOnClickListener {
            Toast.makeText(this, "Has premut el botó!", Toast.LENGTH_SHORT).show()
            //TODO(crida a activity per visualitzar i reproduir)
        }
        btnServeisOnline.setOnClickListener {
            Toast.makeText(this, "Has premut el botó!", Toast.LENGTH_SHORT).show()
            //TODO(crida a funció per obrir activity se serveis online)
        }
    }
}