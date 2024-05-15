package asb.m08.UF2_1_1.AplicacioMultimediaAlexS

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Classes.Text
import asb.m08.UF2_1_1.AplicacioMultimediaAlexS.Objectes.Txt_IO

class Text_Activity : AppCompatActivity() {
    lateinit var etText: EditText
    lateinit var etNomArxiu: EditText
    lateinit var rgEstil: RadioGroup
    lateinit var rbNormal: RadioButton
    lateinit var rbNegreta: RadioButton
    lateinit var rbCursiva: RadioButton
    lateinit var rgMida: RadioGroup
    lateinit var rbPetita: RadioButton
    lateinit var rbMitjana: RadioButton
    lateinit var rbGran: RadioButton
    lateinit var btnPrevisualitzar: Button
    lateinit var btnDesarArxiu: Button
    lateinit var tvPrevisualitzar: TextView

    var estilNormal: Boolean = false
    var estilNegreta: Boolean = false
    var estilCursiva: Boolean = false

    var fontPetita: Boolean = false
    var fontMitjana: Boolean = false
    var fontGran: Boolean = false

    val extensioArxiu = ".txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        etText = findViewById(R.id.etText)
        etNomArxiu = findViewById(R.id.etNomArxiu)
        rgEstil = findViewById(R.id.rgEstil)
        rbNormal = findViewById(R.id.rbNormal)
        rbNegreta = findViewById(R.id.rbNegreta)
        rbCursiva = findViewById(R.id.rbCursiva)
        rgMida = findViewById(R.id.rgMida)
        rbPetita = findViewById(R.id.rbPetita)
        rbMitjana = findViewById(R.id.rbMitjana)
        rbGran = findViewById(R.id.rbGran)
        btnPrevisualitzar = findViewById(R.id.btnPrevisualitzar)
        btnDesarArxiu = findViewById(R.id.btnDesarArxiu)
        tvPrevisualitzar = findViewById(R.id.tvPrevisualitzar)

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
            var escrit = false
            if (!etText.text.isNullOrEmpty()){
                if (!etNomArxiu.text.isNullOrEmpty()) {
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

                    val text = Text(etText.text.toString(),estil, mida)
                    val nomArxiu = etNomArxiu.text.toString() + extensioArxiu
                    escrit = Txt_IO.TextFileHandler().writeText(text, nomArxiu)
                } else {
                    Toast.makeText(this, "El nom de l'arxiu no pot estar en blanc", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "El text no pot estar en blanc", Toast.LENGTH_SHORT).show()
            }
            if (escrit) {
                Toast.makeText(this, "Arxiu desat correctament", Toast.LENGTH_SHORT).show()
                inicialitzarActivity()
            } else {
                Toast.makeText(this, "Error en desar l'arxiu", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun previsualitzar() {
        if (!etText.text.isNullOrEmpty()){

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
        } else {
            Toast.makeText(this, "El text no pot estar en blanc", Toast.LENGTH_SHORT).show()
        }
    }
    private fun resetCamps() {
        var textNormal = false
        var textNegreta = false
        var textCursiva = false

        var fontPetita = false
        var fontNormal = false
        var fontGran = false

        etText.setText("")
        etNomArxiu.setText("")
        tvPrevisualitzar.setText("")
    }

    private fun inicialitzarActivity() {
        resetCamps()
        val rbNormal = findViewById<RadioButton>(R.id.rbNormal)
        val rbMitjana = findViewById<RadioButton>(R.id.rbMitjana)
        rbNormal.isChecked = true;
        rbMitjana.isChecked = true;
    }
}