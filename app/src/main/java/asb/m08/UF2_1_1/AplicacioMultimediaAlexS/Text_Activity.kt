package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

class Text_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        val etText = findViewById<EditText>(R.id.etText)
        val etNomArxiu = findViewById<EditText>(R.id.etNomArxiu)
        val rbNormal = findViewById<RadioButton>(R.id.rbNormal)
        val rbNegreta = findViewById<RadioButton>(R.id.rbNegreta)
        val rbCursiva = findViewById<RadioButton>(R.id.rbCursiva)
        val rbPetita = findViewById<RadioButton>(R.id.rbPetita)
        val rbMitjana = findViewById<RadioButton>(R.id.rbMitjana)
        val rbGran = findViewById<RadioButton>(R.id.rbGran)
        val btnPrevisualitzar = findViewById<Button>(R.id.btnPrevisualitzar)
        val tvPrevisualitzar = findViewById<TextView>(R.id.tvPrevisualitzar)
        val btnDesarArxiu = findViewById<Button>(R.id.btnDesarArxiu)

    }
}