package asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Audio_IO {

    const val REQUEST_AUDIO_CAPTURE = 2
    const val REQUEST_PERMISSIONS = 20
    private var audioURI: Uri? = null

    fun startAudioCaptureProcess(activity: Activity) {
        val permissionsNeeded = checkAndRequestPermissions(activity)
        if (permissionsNeeded.isEmpty()) {
            dispatchRecordAudioIntent(activity)
        } else {
            ActivityCompat.requestPermissions(activity, permissionsNeeded.toTypedArray(), REQUEST_PERMISSIONS)
        }
    }

    private fun checkAndRequestPermissions(activity: Activity): List<String> {
        val recordPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
        val writePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val listPermissionsNeeded = mutableListOf<String>()
        if (recordPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO)
        }
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        return listPermissionsNeeded
    }

    fun dispatchRecordAudioIntent(activity: Activity) {
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

    @Throws(IOException::class)
    private fun createAudioFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val audioFileName = "AUDIO_${timeStamp}_"
        //val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val storageDir: File? = Permanent.audioDir
            return File.createTempFile(audioFileName, ".mp3", storageDir)
    }

    fun handleActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_AUDIO_CAPTURE && resultCode == Activity.RESULT_OK) {
            showAudioNameDialog(activity)
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(activity, "Audio capture cancelled", Toast.LENGTH_SHORT).show()
        }
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

    private fun saveAudioWithName(activity: Activity, audioName: String) {
        // Implementa la lògica per guardar l'àudio amb el nom especificat
        Toast.makeText(activity, "Audio saved as: $audioName", Toast.LENGTH_SHORT).show()
    }
}
