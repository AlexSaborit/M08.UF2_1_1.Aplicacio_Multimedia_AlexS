package asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Classes

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class Permisos {
    fun checkAndRequestPermissions(activity: Activity): List<String> {
        val listPermissionsNeeded = mutableListOf<String>()

        // Comprova si la versió d'Android és anterior a 33
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {

            val cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }

            val audioPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
            if (audioPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO)
            }

            val writePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU){
            // Si la versió d'Android és 33 o posterior, només demana el permís de càmera
            val cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }

            val videoPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO)
            if (videoPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_VIDEO)
            }

            val audioPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO)
            if (audioPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_AUDIO)
            }

            val writePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES)
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }

        return listPermissionsNeeded
    }
}