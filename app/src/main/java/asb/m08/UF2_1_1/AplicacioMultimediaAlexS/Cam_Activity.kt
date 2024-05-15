package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class Cam_Activity : AppCompatActivity() {//NO S'USA
    lateinit var ivCam: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cam)

        val btnCam1 = findViewById<Button>(R.id.btnCam1)
        ivCam = findViewById<ImageView>(R.id.ivCam)

        btnCam1.setOnClickListener {
            takePhoto()
        }
    }
    val getAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val bitmap = it.data?.extras?.get("data") as Bitmap
        ivCam.setImageBitmap(bitmap)
    }
    fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            getAction
        } else {
            Toast.makeText(this, "No s'ha pogut obrir la càmera", Toast.LENGTH_SHORT).show()
        }
    }
}