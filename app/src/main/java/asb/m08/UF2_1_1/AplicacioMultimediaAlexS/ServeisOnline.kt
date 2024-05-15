package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ServeisOnline : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serveis_online)

        val editTextGoogleMaps = findViewById<EditText>(R.id.editTextGoogleMaps)
        val btnGoogleMaps = findViewById<Button>(R.id.btnGoogleMaps)

        val editTextWikipedia = findViewById<EditText>(R.id.editTextWikipedia)
        val btnWikipedia = findViewById<Button>(R.id.btnWikipedia)

        val editTextXarxesSocials = findViewById<EditText>(R.id.editTextXarxesSocials)
        val btnXarxesSocials = findViewById<Button>(R.id.btnXarxesSocials)

        btnGoogleMaps.setOnClickListener {
            val adreca = editTextGoogleMaps.text.toString()
            if (adreca.isNotEmpty()) {
                val gmmIntentUri = Uri.parse("geo:0,0?q=$adreca")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            } else {
                showAlertDialog("Google Maps", "Si us plau, introdueix una adreça.")
            }
        }

        btnWikipedia.setOnClickListener {
            val article = editTextWikipedia.text.toString()
            if (article.isNotEmpty()) {
                val wikiIntentUri = Uri.parse("https://ca.wikipedia.org/wiki/$article")
                val wikiIntent = Intent(Intent.ACTION_VIEW, wikiIntentUri)
                startActivity(wikiIntent)
            } else {
                showAlertDialog("Wikipedia", "Si us plau, introdueix un article.")
            }
        }

        btnXarxesSocials.setOnClickListener {
            val url = editTextXarxesSocials.text.toString()
            if (url.isNotEmpty()) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, url)
                val chooser = Intent.createChooser(shareIntent, "Compartir URL amb:")
                startActivity(chooser)
            } else {
                showAlertDialog("Xarxes Socials", "Si us plau, introdueix una URL.")
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}
