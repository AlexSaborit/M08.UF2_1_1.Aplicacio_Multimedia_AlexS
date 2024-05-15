package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.VideoView
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Visualitzar_Reproduir_Editar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualitzar_reproduir_editar)

        val arxiuSeleccionat = intent.getStringExtra("ARXIU_SELECCIONAT")
        val tipusArxiu = intent.getStringExtra("TIPUS_ARXIU")

        if (arxiuSeleccionat != null && tipusArxiu != null) {
            val arxiuUri = Uri.parse(arxiuSeleccionat)

            when (tipusArxiu) {
                "imatge" -> {
                    val imageView: ImageView = findViewById(R.id.image_view)
                    imageView.setImageURI(arxiuUri)
                    imageView.visibility = ImageView.VISIBLE
                }
                "video", "so" -> {
                    val videoView: VideoView = findViewById(R.id.video_view)
                    videoView.setVideoURI(arxiuUri)
                    videoView.setMediaController(MediaController(this))
                    videoView.visibility = VideoView.VISIBLE
                    videoView.start()
                }
                else -> {
                    Toast.makeText(this, "Tipus d'arxiu no suportat", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "No s'ha seleccionat cap arxiu", Toast.LENGTH_SHORT).show()
        }
    }
}
