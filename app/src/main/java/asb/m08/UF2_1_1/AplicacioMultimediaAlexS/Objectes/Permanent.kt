package asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes

import android.os.Environment

object Permanent {
    val textDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val imgDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val audioDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
    val videoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)

    lateinit var llistatPermisos: List<String>
}