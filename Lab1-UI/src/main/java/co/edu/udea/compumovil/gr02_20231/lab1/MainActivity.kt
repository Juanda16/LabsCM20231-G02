package co.edu.udea.compumovil.gr02_20231.lab1

//import co.edu.udea.compumovil.gr02_20231.lab1.databinding.perspnal_data_activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import co.edu.udea.compumovil.gr02_20231.lab1.databinding.PersonalDataActivityBinding
import co.edu.udea.compumovil.gr02_20231.lab1.model.Person
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


//@SuppressLint("StaticFieldLeak")
//var boton: Button? = null
//@SuppressLint("StaticFieldLeak")
//var cajaFecha : EditText? = null


class MainActivity : AppCompatActivity() {
    //class MainActivity : AppCompatActivity(), OnClickListener{

    var selectedDate: Date? = null

    private lateinit var binding: PersonalDataActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.personal_data_activity)

        binding = PersonalDataActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val schoolGrade = resources.getStringArray(R.array.school_grade)
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item,
            schoolGrade
        )
        var textView: AutoCompleteTextView = findViewById(R.id.lista)
        textView.setAdapter(adapter)


        //inicializarComponentes();

        birthDateFocusListener()

    }
    private fun submitForm() {
        //TODO: Validar campos

            var person = Person(
               "Name",
                "lastName",
                "gender",
                getDateString(selectedDate),
                "educationLevel",
            )

            val intent = Intent(this@MainActivity, ContactDataActivity::class.java)
            intent.putExtra("Person", person);
            startActivity(intent)
    }

    private fun birthDateFocusListener() {

        binding.birthdayContainer.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText(getString(R.string.select_date))
                .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
            datePicker.addOnPositiveButtonClickListener {
                selectedDate = Date(datePicker.selection!!)

                binding.birthdayContainer.editText?.setText(datePicker.headerText)
                if (validateBirthDate() == null) {
                    binding.birthdayContainer.error = null
                    binding.birthdayContainer.helperText = getString(R.string.required)
                } else {
                    binding.birthdayContainer.error = validateBirthDate()
                }
            }

        }


    }

    private fun validateBirthDate(): String? {
        val dateInput = binding.birthdayContainer.editText?.text.toString()
        if (dateInput.isEmpty()) {
            return getString(R.string.required)
        }
        if (selectedDate?.after(Date()) == true) {
            return getString(R.string.invalid_date)
        }
        return null
    }

    private fun getDateString(selectedDate: Date?): String {
        return if (selectedDate != null) {
            selectedDate.time
            "${selectedDate.date+1}/${selectedDate.month+1}/${selectedDate.year+1900}"
        } else {
            ""
        }

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




