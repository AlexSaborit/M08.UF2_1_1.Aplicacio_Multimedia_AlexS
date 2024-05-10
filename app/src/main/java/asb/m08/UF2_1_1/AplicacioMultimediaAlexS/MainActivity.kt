package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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

    }
}