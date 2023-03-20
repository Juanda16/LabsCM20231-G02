package co.edu.udea.compumovil.gr02_20231.lab1


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity



//@SuppressLint("StaticFieldLeak")
//var boton: Button? = null
//@SuppressLint("StaticFieldLeak")
//var cajaFecha : EditText? = null


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this@MainActivity, PersonalDataActivity::class.java)
        startActivity(intent)


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


}




