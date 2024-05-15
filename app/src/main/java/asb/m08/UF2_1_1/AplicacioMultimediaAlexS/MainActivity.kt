package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Audio_IO
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Picture_IO
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Video_IO

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
            //val intent = Intent(this, Cam_Activity::class.java)
            //startActivity(intent)
            Picture_IO.startImageCaptureProcess(this)
        }
        btnCapturarVideo.setOnClickListener {
            Video_IO.startVideoCaptureProcess(this)
        }
        btnCapturarSo.setOnClickListener {
            Audio_IO.startAudioCaptureProcess(this)
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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Picture_IO.REQUEST_PERMISSIONS) {
            val picturePermissionsGranted = permissions.mapIndexed { index, permission -> permission to grantResults[index] }
                .toMap()
                .all { it.value == PackageManager.PERMISSION_GRANTED }

            if (picturePermissionsGranted) {
                Picture_IO.dispatchTakePictureIntent(this)
            } else {
                Toast.makeText(this, "Permissions Denied for Picture", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == Audio_IO.REQUEST_PERMISSIONS) {
            val audioPermissionsGranted = permissions.mapIndexed { index, permission -> permission to grantResults[index] }
                .toMap()
                .all { it.value == PackageManager.PERMISSION_GRANTED }

            if (audioPermissionsGranted) {
                Audio_IO.dispatchRecordAudioIntent(this)
            } else {
                Toast.makeText(this, "Permissions Denied for Audio", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == Video_IO.REQUEST_PERMISSIONS) {
            val videoPermissionsGranted = permissions.mapIndexed { index, permission -> permission to grantResults[index] }
                .toMap()
                .all { it.value == PackageManager.PERMISSION_GRANTED }

            if (videoPermissionsGranted) {
                Video_IO.dispatchRecordVideoIntent(this)
            } else {
                Toast.makeText(this, "Permissions Denied for Video", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Picture_IO.REQUEST_IMAGE_CAPTURE) {
            Picture_IO.handleActivityResult(this, requestCode, resultCode, data)
        } else if (requestCode == Audio_IO.REQUEST_AUDIO_CAPTURE) {
            Audio_IO.handleActivityResult(this, requestCode, resultCode, data)
        } else if (requestCode == Video_IO.REQUEST_VIDEO_CAPTURE) {
            Video_IO.handleActivityResult(this, requestCode, resultCode, data)
        }
    }
}