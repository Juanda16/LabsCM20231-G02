package co.edu.udea.compumovil.gr02_20231.lab1

import android.app.Activity
import android.app.ActivityManager
import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputBinding
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import co.edu.udea.compumovil.gr02_20231.lab1.databinding.ActivityMainBinding
import java.util.Calendar

var boton: Button? = null
var cajaFecha : EditText? = null


class MainActivity : AppCompatActivity(), OnClickListener{

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val schoolGrade = resources.getStringArray(R.array.school_grade)
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item,
            schoolGrade
        )




        inicializarComponentes();
    }


    private fun inicializarComponentes(){
        boton = findViewById(R.id.btnChange)
        cajaFecha= findViewById(R.id.datePlace)
        boton?.setOnClickListener (this)
    }

    override fun onClick(p0: View?) {
        val Dialogfecha = DatePickerFragment {year, month, day -> mostrarResultado(year, month, day) }
        Dialogfecha.show(supportFragmentManager,"DatePicker")
    }

    private fun mostrarResultado(year: Int, month: Int, day: Int) {
        cajaFecha?.setText("$year/$month/$day")

    }

    class DatePickerFragment (val listener: (year:Int, month:Int, day:Int)-> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog (requireActivity(), this, year, month, day)
        }

        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year, month, day)
        }
    }
}




