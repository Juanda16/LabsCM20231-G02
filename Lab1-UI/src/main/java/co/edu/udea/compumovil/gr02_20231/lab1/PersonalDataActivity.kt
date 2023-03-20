package co.edu.udea.compumovil.gr02_20231.lab1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import co.edu.udea.compumovil.gr02_20231.lab1.databinding.ActivityPersonalDataBinding
import co.edu.udea.compumovil.gr02_20231.lab1.model.Person
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


class PersonalDataActivity : AppCompatActivity() {

    var selectedDate : Date? = null
    private lateinit var binding: ActivityPersonalDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_personal_data)
        setContentView(binding.root)

        val schoolGrade = resources.getStringArray(R.array.school_grade)
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item,
            schoolGrade
        )
        val textView: AutoCompleteTextView = findViewById(R.id.lista)
        textView.setAdapter(adapter)

        birthDateFocusListener()

        binding.NextBtn.setOnClickListener{verifyForm()}
    }

    private fun verifyForm() {
        //TODO: Validar campos


        var person = Person(
            "Name",
            "lastName",
            "gender",
            getDateString(selectedDate),
            "educationLevel",
        )

        val intent = Intent(this@PersonalDataActivity, ContactDataActivity::class.java)
        intent.putExtra("Person", person);
        startActivity(intent)
    }

    private fun birthDateFocusListener() {

        binding.birthdayContainer?.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText(getString(R.string.select_date))
                .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
            datePicker.addOnPositiveButtonClickListener {
                selectedDate = Date(datePicker.selection!!)

                binding.birthdayContainer!!.editText?.setText(datePicker.headerText)
                if (validateBirthDate() == null) {
                    binding.birthdayContainer!!.error = null
                    binding.birthdayContainer!!.helperText = getString(R.string.required)
                } else {
                    binding.birthdayContainer!!.error = validateBirthDate()
                }
            }

        }


    }

    private fun validateBirthDate(): String? {
        val dateInput = binding.birthdayContainer?.editText?.text.toString()
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
            "${selectedDate.date + 1}/${selectedDate.month + 1}/${selectedDate.year + 1900}"
        } else {
            ""
        }

    }

    fun onRadioButtonClicked(view: View) {
        /* if (view is RadioButton) {
             // Is the button now checked?
             val checked = view.isChecked
             var gender = String

             // Check which radio button was clicked
             when (view.getId()) {
                 R.id.radioButtonM ->
                     if (checked) {
                        gender="man" // load man
                     }
                 R.id.radioButtonW ->
                     if (checked) {
                         // load gender=Women
                     }
             }//return gender
         }*/
    }

    /*private fun verifyForm(){
        binding.nameContainer.helperText = validateName()
        binding.nameContainer.helperText = validateLastName()

        val valid = binding.nameContainer.helperText == null &&
                binding.lastNameContainer.helperText == null

        if (valid) {
            //
        } else {
            //
        }

    }*/


    private fun validateName(): String? {
        val nameText = binding.nameContainer.editText?.text.toString()
        if (nameText.isEmpty()) {
            return getString(R.string.required)
        }
        return null
    }

    private fun validateLastName(): String? {
        val nameText = binding.lastNameContainer.editText?.text.toString()
        if (nameText.isEmpty()) {
            return getString(R.string.required)
        }
        return null
    }


    fun sendMessage(view: View) {
        val intent = Intent(this@PersonalDataActivity, ContactDataActivity::class.java)
        startActivity(intent)
    }
}