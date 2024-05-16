package asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes

import android.Manifest
import android.app.Activity
import android.content.Context
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
import androidx.core.content.FileProvider
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Cam_Activity
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Text_Activity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Picture_IO {

    const val REQUEST_IMAGE_CAPTURE = 1
    const val REQUEST_PERMISSIONS = 10
    private var photoURI: Uri? = null

    fun startImageCaptureProcess(activity: Activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //val permissionsNeeded = checkAndRequestPermissions(activity)
            val permissionsNeeded = Permanent.llistatPermisos
            if (permissionsNeeded.isNotEmpty()) {
                ActivityCompat.requestPermissions(activity, permissionsNeeded.toTypedArray(), REQUEST_PERMISSIONS)
                return
            }
        }
        dispatchTakePictureIntent(activity)
    }

    private fun checkAndRequestPermissions(activity: Activity): List<String> {
        val listPermissionsNeeded = mutableListOf<String>()

        // Comprova si la versió d'Android és anterior a 33
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            val cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            val writePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU){
            // Si la versió d'Android és 33 o posterior, només demana el permís de càmera
            val cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }
            val writePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES)
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }

        return listPermissionsNeeded
    }


    fun dispatchTakePictureIntent(activity: Activity) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                try {
                    val photoFile: File? = createImageFile(activity)
                    photoFile?.also {
                        photoURI = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                            Uri.fromFile(it)
                        } else {
                            FileProvider.getUriForFile(
                                activity,
                                //"com.example.myapp.fileprovider",
                                "asb.m08.UF2_1_1.AplicacioMultimediaAlexSfileprovider",
                                it
                            )
                        }
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                } catch (ex: IOException) {
                    Toast.makeText(activity, "Error creating image file", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir: File? = Permanent.imgDir
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    fun handleActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            showImageNameDialog(activity)
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(activity, "Image capture cancelled", Toast.LENGTH_SHORT).show()
        }
    }

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

    private fun saveImageWithName(activity: Activity, imageName: String) {
        // Implementa la lògica per guardar la imatge amb el nom especificat
        Toast.makeText(activity, "Image saved as: $imageName", Toast.LENGTH_SHORT).show()
    }
}
