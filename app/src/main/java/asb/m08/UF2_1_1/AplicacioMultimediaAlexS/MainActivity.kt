package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Classes.Permisos
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Audio_IO
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Permanent
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Picture_IO
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Video_IO

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_CERCAR_ARXIU = 1
        val REQUEST_IMAGE_CAPTURE = 1
        val REQUEST_VIDEO_CAPTURE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCrearTxt = findViewById<Button>(R.id.btnCrearTxt)
        val btnCapturarFoto = findViewById<Button>(R.id.btnCapturarFoto)
        val btnCapturarVideo = findViewById<Button>(R.id.btnCapturarVideo)
        val btnCapturarSo = findViewById<Button>(R.id.btnCapturarSo)
        val btnVisualitzarReproduir = findViewById<Button>(R.id.btnVisualitzarReproduir)
        val btnServeisOnline = findViewById<Button>(R.id.btnServeisOnline)

        val permisos = Permisos()
        Permanent.llistatPermisos = permisos.checkAndRequestPermissions(this)

        btnCrearTxt.setOnClickListener {
            val intent = Intent(this, Text_Activity::class.java)
            startActivity(intent)
        }
        btnCapturarFoto.setOnClickListener {
            dispatchTakePictureIntent()
            Picture_IO.startImageCaptureProcess(this)
        }
        btnCapturarVideo.setOnClickListener {
            dispatchTakeVideoIntent()
            Video_IO.startVideoCaptureProcess(this)
        }
        btnCapturarSo.setOnClickListener {
            Audio_IO.startAudioCaptureProcess(this)
        }
        btnVisualitzarReproduir.setOnClickListener {
            val intent = Intent(this, Tria_Arxiu::class.java)
            startActivity(intent)
        }
        btnServeisOnline.setOnClickListener {
            val intent = Intent(this, ServeisOnline::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Picture_IO.REQUEST_PERMISSIONS) {
            handlePermissionsResult(
                grantResults,
                "Permissions Denied for Picture",
                { Picture_IO.dispatchTakePictureIntent(this) }
            )
        } else if (requestCode == Audio_IO.REQUEST_PERMISSIONS) {
            handlePermissionsResult(
                grantResults,
                "Permissions Denied for Audio",
                { Audio_IO.dispatchRecordAudioIntent(this) }
            )
        } else if (requestCode == Video_IO.REQUEST_PERMISSIONS) {
            handlePermissionsResult(
                grantResults,
                "Permissions Denied for Video",
                { Video_IO.dispatchRecordVideoIntent(this) }
            )
        }
    }

    private fun handlePermissionsResult(
        grantResults: IntArray,
        deniedMessage: String,
        onPermissionsGranted: () -> Unit
    ) {
        if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            onPermissionsGranted()
        } else {
            Toast.makeText(this, deniedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Picture_IO.REQUEST_IMAGE_CAPTURE -> Picture_IO.handleActivityResult(this, requestCode, resultCode, data)
            Audio_IO.REQUEST_AUDIO_CAPTURE -> Audio_IO.handleActivityResult(this, requestCode, resultCode, data)
            Video_IO.REQUEST_VIDEO_CAPTURE -> Video_IO.handleActivityResult(this, requestCode, resultCode, data)
            REQUEST_CODE_CERCAR_ARXIU -> if (resultCode == RESULT_OK) {
                val arxiuSeleccionat = data?.getStringExtra("ARXIU_SELECCIONAT")
                val tipusArxiu = data?.getStringExtra("TIPUS_ARXIU")
                if (arxiuSeleccionat != null && tipusArxiu != null) {
                    val intent = Intent(this, Visualitzar_Reproduir_Editar::class.java)
                    intent.putExtra("ARXIU_SELECCIONAT", arxiuSeleccionat)
                    intent.putExtra("TIPUS_ARXIU", tipusArxiu)
                    startActivity(intent)
                }
            }
        }
    }
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "error foto cam intent", Toast.LENGTH_SHORT).show()
            // display error state to the user
        }
    }
    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            } ?: run {
                Toast.makeText(this, "error video cam intent", Toast.LENGTH_SHORT).show()
                //display error state to the user
            }
        }
    }
}
