package co.edu.udea.compumovil.gr02_20231.lab1

//import co.edu.udea.compumovil.gr02_20231.lab1.databinding.perspnal_data_activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import co.edu.udea.compumovil.gr02_20231.lab1.databinding.ActivityMainBinding


//@SuppressLint("StaticFieldLeak")
//var boton: Button? = null
//@SuppressLint("StaticFieldLeak")
//var cajaFecha : EditText? = null

import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    //class MainActivity : AppCompatActivity(), OnClickListener{


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.personal_data_activity)
        //setContentView(binding.root)
        val schoolGrade = resources.getStringArray(R.array.school_grade)
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item,
            schoolGrade
        )
        var textView: AutoCompleteTextView = findViewById(R.id.lista)
        textView.setAdapter(adapter)




        //inicializarComponentes();
    }


//    private fun inicializarComponentes(){
//        boton = findViewById(R.id.btnChange)
//        cajaFecha= findViewById(R.id.datePlace)
//        boton?.setOnClickListener (this)
//    }
//
//    override fun onClick(p0: View?) {
//        val Dialogfecha = DatePickerFragment {year, month, day -> mostrarResultado(year, month, day) }
//        Dialogfecha.show(supportFragmentManager,"DatePicker")
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun mostrarResultado(year: Int, month: Int, day: Int) {
//        cajaFecha?.setText("$year/$month/$day")
//
//    }
//
//    class DatePickerFragment (val listener: (year:Int, month:Int, day:Int)-> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener {
//
//        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//            val c = Calendar.getInstance()
//            var year = c.get(Calendar.YEAR)
//            var month = c.get(Calendar.MONTH)
//            var day = c.get(Calendar.DAY_OF_MONTH)
//
//            return DatePickerDialog (requireActivity(), this, year, month, day)
//        }
//
//        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
//            listener(year, month, day)
//        }
//    }

    fun sendMessage(view: View) {
        val intent = Intent(this@MainActivity, ContactDataActivity::class.java)
        startActivity(intent)
    }
}




