package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Classes.Permisos
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Permanent
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    companion object {
        val REQUEST_CODE_CERCAR_ARXIU = 101 //TODO(revisar si ha de ser un altre número per evitar repetir el valor 1)
        val REQUEST_IMAGE_CAPTURE = 1
        val REQUEST_AUDIO_CAPTURE = 2
        val REQUEST_VIDEO_CAPTURE = 3

        val REQUEST_PERMISSIONS_image = 10
        val REQUEST_PERMISSIONS_audio = 20
        val REQUEST_PERMISSIONS_video = 30

        private var audioURI: Uri? = null
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
        permisos.requestPermissions(this, Permanent.llistatPermisos, REQUEST_PERMISSIONS_image)//permís per foto
        permisos.requestPermissions(this, Permanent.llistatPermisos, REQUEST_PERMISSIONS_audio)//permís per audio
        permisos.requestPermissions(this, Permanent.llistatPermisos, REQUEST_PERMISSIONS_video)//permís per vídeo
        //permisos.requestPermissions(this, Permanent.llistatPermisos, 10)//permís per llegir/escriure

        btnCrearTxt.setOnClickListener {
            val intent = Intent(this, Text_Activity::class.java)
            startActivity(intent)
        }
        btnCapturarFoto.setOnClickListener {
            dispatchTakePictureIntent()
            //Picture_IO.startImageCaptureProcess(this)
        }
        btnCapturarVideo.setOnClickListener {
            dispatchTakeVideoIntent()
            //Video_IO.startVideoCaptureProcess(this)
        }
        btnCapturarSo.setOnClickListener {
            //Audio_IO.startAudioCaptureProcess(this)
            dispatchRecordAudioIntent(this)
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

        if (requestCode == REQUEST_PERMISSIONS_image) {
            handlePermissionsResult(
                grantResults,
                "Permissions Denied for Picture",
                //{ Picture_IO.dispatchTakePictureIntent(this) }
                { dispatchTakePictureIntent() }
            )
        } else if (requestCode == REQUEST_PERMISSIONS_audio) {
            handlePermissionsResult(
                grantResults,
                "Permissions Denied for Audio",
                //{ Audio_IO.dispatchRecordAudioIntent(this) }
                { dispatchRecordAudioIntent(this) }
            )
        } else if (requestCode == REQUEST_PERMISSIONS_video) {
            handlePermissionsResult(
                grantResults,
                "Permissions Denied for Video",
                //{ Video_IO.dispatchRecordVideoIntent(this) }
                { dispatchTakeVideoIntent() }
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
            REQUEST_IMAGE_CAPTURE -> handleActivityResult(this, requestCode, resultCode, data)
            REQUEST_AUDIO_CAPTURE -> handleActivityResult(this, requestCode, resultCode, data)
            REQUEST_VIDEO_CAPTURE -> handleActivityResult(this, requestCode, resultCode, data)
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
    /*private fun dispatchTakePictureIntent() {
        //https://developer.android.com/media/camera/camera-intents
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "error foto cam intent", Toast.LENGTH_SHORT).show()
            // display error state to the user
        }
    }*/
    /*private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            } ?: run {
                Toast.makeText(this, "error video cam intent", Toast.LENGTH_SHORT).show()
                //display error state to the user
            }
        }
    }*/
    /*fun dispatchRecordAudioIntent(activity: Activity) {
        Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION).also { recordSoundIntent ->
            recordSoundIntent.resolveActivity(activity.packageManager)?.also {
                try {
                    val audioFile: File? = createAudioFile(activity)
                    audioFile?.also {
                        audioURI = Uri.fromFile(audioFile)
                        recordSoundIntent.putExtra(MediaStore.EXTRA_OUTPUT, audioURI)
                        activity.startActivityForResult(recordSoundIntent,
                            REQUEST_AUDIO_CAPTURE
                        )
                    }
                } catch (ex: IOException) {
                    Toast.makeText(activity, "Error creating audio file", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/
    fun handleActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            showImageNameDialog(activity)
        } else if (requestCode == REQUEST_AUDIO_CAPTURE && resultCode == Activity.RESULT_OK) {
            showAudioNameDialog(activity)
        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == Activity.RESULT_OK) {
            showVideoNameDialog(activity)
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(activity, "capture cancelled", Toast.LENGTH_SHORT).show()
        }
    }/*
    @Throws(IOException::class)
    private fun createAudioFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val audioFileName = "AUDIO_${timeStamp}_"
        val storageDir: File? = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        return File.createTempFile(audioFileName, ".mp3", storageDir)
    }*/
    private fun showImageNameDialog(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Enter image name")

        val input = EditText(activity)
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, _ ->
            var imageName = input.text.toString()
            if (!imageName.endsWith(".jpg")) {
                imageName += ".jpg"
            }
            saveImageWithName(activity, imageName)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun showAudioNameDialog(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Enter audio name")

        val input = EditText(activity)
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, _ ->
            var audioName = input.text.toString()
            if (!audioName.endsWith(".mp3")) {
                audioName += ".mp3"
            }
            saveAudioWithName(activity, audioName)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun showVideoNameDialog(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Enter video name")

        val input = EditText(activity)
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, _ ->
            var videoName = input.text.toString()
            if (!videoName.endsWith(".mp4")) {
                videoName += ".mp4"
            }
            saveVideoWithName(activity, videoName)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun saveAudioWithName(activity: Activity, audioName: String) {
        // Implementa la lògica per guardar l'àudio amb el nom especificat
        Toast.makeText(activity, "Audio saved as: $audioName", Toast.LENGTH_SHORT).show()
    }

    private fun saveVideoWithName(activity: Activity, videoName: String) {
        // Implementa la lògica per guardar el vídeo amb el nom especificat
        Toast.makeText(activity, "Video saved as: $videoName", Toast.LENGTH_SHORT).show()
    }

    private fun saveImageWithName(activity: Activity, imageName: String) {
        // Implementa la lògica per guardar la imatge amb el nom especificat
        Toast.makeText(activity, "Image saved as: $imageName", Toast.LENGTH_SHORT).show()
    }
    fun dispatchTakePictureIntent() {
        // No es crida directament Picture_IO, sinó que es fa servir l'intent directament aquí
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "error foto cam intent", Toast.LENGTH_SHORT).show()
            // display error state to the user
        }
    }

    fun dispatchTakeVideoIntent() {
        // No es crida directament Video_IO, sinó que es fa servir l'intent directament aquí
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            } ?: run {
                Toast.makeText(this, "error video cam intent", Toast.LENGTH_SHORT).show()
                //display error state to the user
            }
        }
    }

    fun dispatchRecordAudioIntent(activity: Activity) {
        // Es crida la funció de creació de fitxer d'àudio dins d'aquesta classe
        Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION).also { recordSoundIntent ->
            recordSoundIntent.resolveActivity(activity.packageManager)?.also {
                try {
                    val audioFile: File? = createAudioFile(activity)
                    audioFile?.also {
                        audioURI = Uri.fromFile(audioFile)
                        recordSoundIntent.putExtra(MediaStore.EXTRA_OUTPUT, audioURI)
                        activity.startActivityForResult(recordSoundIntent, REQUEST_AUDIO_CAPTURE)
                    }
                } catch (ex: IOException) {
                    Toast.makeText(activity, "Error creating audio file", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun createAudioFile(context: Context): File {
        // S'utilitza la carpeta d'àudio emmagatzemada a Permanent.kt
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val audioFileName = "AUDIO_${timeStamp}_"
        val storageDir: File? = Permanent.audioDir
        return File.createTempFile(audioFileName, ".mp3", storageDir)
    }

}


