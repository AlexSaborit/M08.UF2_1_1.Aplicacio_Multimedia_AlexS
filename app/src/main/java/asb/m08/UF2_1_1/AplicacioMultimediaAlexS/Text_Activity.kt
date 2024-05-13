package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class Text_Activity : AppCompatActivity() {
    var estilNormal: Boolean = false
    var estilNegreta: Boolean = false
    var estilCursiva: Boolean = false

    var fontPetita: Boolean = false
    var fontNormal: Boolean = false
    var fontGran: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        val etText = findViewById<EditText>(R.id.etText)
        val etNomArxiu = findViewById<EditText>(R.id.etNomArxiu)
        val rgEstil = findViewById<RadioGroup>(R.id.rgEstil)
        val rbNormal = findViewById<RadioButton>(R.id.rbNormal)
        val rbNegreta = findViewById<RadioButton>(R.id.rbNegreta)
        val rbCursiva = findViewById<RadioButton>(R.id.rbCursiva)
        val rgMida = findViewById<RadioGroup>(R.id.rgMida)
        val rbPetita = findViewById<RadioButton>(R.id.rbPetita)
        val rbMitjana = findViewById<RadioButton>(R.id.rbMitjana)
        val rbGran = findViewById<RadioButton>(R.id.rbGran)
        val btnPrevisualitzar = findViewById<Button>(R.id.btnPrevisualitzar)
        val tvPrevisualitzar = findViewById<TextView>(R.id.tvPrevisualitzar)
        val btnDesarArxiu = findViewById<Button>(R.id.btnDesarArxiu)

        inicialitzarActivity()
        rgEstil.setOnCheckedChangeListener { group, checkedId ->
            if (rbNormal.isChecked) {
                estilNormal = true
                estilNegreta = false
                estilCursiva = false
            }
            if (rbNegreta.isChecked) {
                estilNormal = false
                estilNegreta = true
                estilCursiva = false
            }
            if (rbCursiva.isChecked) {
                estilNormal = false
                estilNegreta = false
                estilCursiva = true
            }
            //TODO(crida a funció per actualitzar la previsualització)
        }
        rgMida.setOnCheckedChangeListener { group, checkedId ->
            if (rbPetita.isChecked) {
                fontPetita = true
                fontNormal = false
                fontGran = false
            }
            if (rbNormal.isChecked) {
                fontPetita = false
                fontNormal = true
                fontGran = false
            }
            if (rbGran.isChecked) {
                fontPetita = false
                fontNormal = false
                fontGran = true
            }
            //TODO(crida a funció per actualitzar la previsualització)
        }
        btnPrevisualitzar.setOnClickListener {
            Toast.makeText(this, "Has premut el botó!", Toast.LENGTH_SHORT).show()
            //TODO(crida a funció per actualitzar la previsualització)
        }
        btnDesarArxiu.setOnClickListener {
            Toast.makeText(this, "Has premut el botó!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetCamps() {
        var textNormal = false
        var textNegreta = false
        var textCursiva = false

        var fontPetita = false
        var fontNormal = false
        var fontGran = false
    }

    private fun inicialitzarActivity() {
        resetCamps()

        var textNormal = true
        var textNegreta = false
        var textCursiva = false

        var fontPetita = false
        var fontNormal = true
        var fontGran = false
    }
}