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

object Video_IO {

    const val REQUEST_VIDEO_CAPTURE = 3
    const val REQUEST_PERMISSIONS = 30
    private var videoURI: Uri? = null

    fun startVideoCaptureProcess(activity: Activity) {
        val permissionsNeeded = checkAndRequestPermissions(activity)
        if (permissionsNeeded.isEmpty()) {
            dispatchRecordVideoIntent(activity)
        } else {
            ActivityCompat.requestPermissions(activity, permissionsNeeded.toTypedArray(), REQUEST_PERMISSIONS)
        }
    }

    private fun checkAndRequestPermissions(activity: Activity): List<String> {
        val cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
        val writePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val listPermissionsNeeded = mutableListOf<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        return listPermissionsNeeded
    }

    fun dispatchRecordVideoIntent(activity: Activity) {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { recordVideoIntent ->
            recordVideoIntent.resolveActivity(activity.packageManager)?.also {
                try {
                    val videoFile: File? = createVideoFile(activity)
                    videoFile?.also {
                        videoURI = Uri.fromFile(videoFile)
                        recordVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
                        activity.startActivityForResult(recordVideoIntent, REQUEST_VIDEO_CAPTURE)
                    }
                } catch (ex: IOException) {
                    Toast.makeText(activity, "Error creating video file", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createVideoFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val videoFileName = "VIDEO_${timeStamp}_"
        //val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        val storageDir: File? = Permanent.videoDir
        return File.createTempFile(videoFileName, ".mp4", storageDir)
    }

    fun handleActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == Activity.RESULT_OK) {
            showVideoNameDialog(activity)
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(activity, "Video capture cancelled", Toast.LENGTH_SHORT).show()
        }
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

    private fun saveVideoWithName(activity: Activity, videoName: String) {
        // Implementa la lògica per guardar el vídeo amb el nom especificat
        Toast.makeText(activity, "Video saved as: $videoName", Toast.LENGTH_SHORT).show()
    }
}
