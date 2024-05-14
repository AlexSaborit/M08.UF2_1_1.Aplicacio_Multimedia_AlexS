package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Classes.Text
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Permanent
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Txt_IO

class Text_Activity : AppCompatActivity() {
    var estilNormal: Boolean = false
    var estilNegreta: Boolean = false
    var estilCursiva: Boolean = false

    var fontPetita: Boolean = false
    var fontMitjana: Boolean = false
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
            previsualitzar()
        }
        rgMida.setOnCheckedChangeListener { group, checkedId ->
            if (rbPetita.isChecked) {
                fontPetita = true
                fontMitjana = false
                fontGran = false
            }
            if (rbMitjana.isChecked) {
                fontPetita = false
                fontMitjana = true
                fontGran = false
            }
            if (rbGran.isChecked) {
                fontPetita = false
                fontMitjana = false
                fontGran = true
            }
            previsualitzar()
        }
        btnPrevisualitzar.setOnClickListener {
            previsualitzar()
        }
        btnDesarArxiu.setOnClickListener {
            var mida = Txt_IO.TextSize.MITJANA
            if (fontPetita) {
                mida = Txt_IO.TextSize.PETITA
            } else if (fontGran) {
                mida = Txt_IO.TextSize.GRAN
            }

            var estil = Txt_IO.TextStyle.NORMAL
            if(estilCursiva) {
                estil = Txt_IO.TextStyle.CURSIVA
            } else if (estilNegreta) {
                estil = Txt_IO.TextStyle.NEGRETA
            }
            val etText = findViewById<EditText>(R.id.etText)
            val etNomArxiu = findViewById<EditText>(R.id.etNomArxiu)
            val text = Text(etText.text.toString(),estil, mida)//TODO (no escullo el nom que ha de tenir l'arxiu)

            var textosLocal = Txt_IO.TextFileHandler(Permanent.textDir).readLines()
            textosLocal.add(text)
            Txt_IO.TextFileHandler(Permanent.textDir).writeLines(textosLocal)
        }
    }

    private fun previsualitzar() {

        val etText = findViewById<EditText>(R.id.etText)
        val tvPrevisualitzar = findViewById<TextView>(R.id.tvPrevisualitzar)

        tvPrevisualitzar.text = etText.text
        if (fontPetita) {
            tvPrevisualitzar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
        }
        if (fontMitjana) {
            tvPrevisualitzar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        }
        if (fontGran) {
            tvPrevisualitzar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        }
        if (estilNormal) {
            tvPrevisualitzar.setTypeface(null, Typeface.NORMAL)
        }
        if (estilCursiva) {
            tvPrevisualitzar.setTypeface(null, Typeface.ITALIC)
        }
        if (estilNegreta) {
            tvPrevisualitzar.setTypeface(null, Typeface.BOLD)
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
        val rbNormal = findViewById<RadioButton>(R.id.rbNormal)
        val rbMitjana = findViewById<RadioButton>(R.id.rbMitjana)
        rbNormal.isChecked = true;
        rbMitjana.isChecked = true;
    }
}