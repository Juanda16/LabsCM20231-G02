package co.edu.udea.compumovil.gr02_20231.lab1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import co.edu.udea.compumovil.gr02_20231.lab1.databinding.ActivityPersonalDataBinding
import co.edu.udea.compumovil.gr02_20231.lab1.model.Person
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


class PersonalDataActivity : AppCompatActivity() {

    var selectedDate: Date? = null
    var gender: String? = null
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


        nameFocusListener()
        lastNameFocusListener()
        genderFocusListener()
        birthDateFocusListener()

        binding.NextBtn.setOnClickListener { verifyForm() }
    }


    private fun nameFocusListener() {
        binding.nameContainer.editText?.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.nameContainer.error = validateName()
            }
        }
    }

    private fun lastNameFocusListener() {
        binding.lastNameContainer.editText?.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.lastNameContainer.error = validateLastName()
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun genderFocusListener() {
        binding.radioGroup?.setOnCheckedChangeListener { _, chekedId ->
            if (
                chekedId == R.id.radioButtonM
            ) {
                gender = getString(R.string.man)
            } else if (
                chekedId == R.id.radioButtonW
            ) {
                gender = getString(R.string.woman)
            }
        }
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


    private fun verifyForm() {

        binding.nameContainer.error = validateName()
        binding.lastNameContainer.error = validateLastName()
        binding.birthdayContainer?.error = validateBirthDate()

        val valid = binding.nameContainer.error == null &&
                binding.lastNameContainer.error == null &&
                binding.birthdayContainer?.error == null

        if (valid) {
            var person = Person(
                binding.nameContainer.editText?.text.toString(),
                binding.lastNameContainer.editText?.text.toString(),
                gender,
                getDateString(selectedDate),
                binding.lista.text.toString(),
            )

            Log.d("personalInfo", "${getString(R.string.personal_info)}:")
            Log.d("personalInfo", "${getString(R.string.name)}: ${binding.nameContainer.editText?.text.toString()}")
            Log.d("personalInfo", "${getString(R.string.last_name)}: ${binding.lastNameContainer.editText?.text.toString()}")
            Log.d("personalInfo", "${getString(R.string.birth_date)}: ${binding.birthdayEditText?.text.toString()}")
            Log.d("personalInfo", "${getString(R.string.gender)}: $gender")
            Log.d("personalInfo", "${getString(R.string.scholarly_level)}: ${binding.lista.text.toString()}")

            val intent = Intent(this@PersonalDataActivity, ContactDataActivity::class.java)
            intent.putExtra("Person", person);
            startActivity(intent)
        } else {
            invalidForm()
        }


    }

    private fun invalidForm() {
        var message = ""
        if (binding.nameContainer.error != null) {
            message += "${getString(R.string.name)}: ${binding.nameContainer.error}"
        }
        if (binding.lastNameContainer.error != null) {
            message += "\n${getString(R.string.last_name)}: ${binding.lastNameContainer.error}"
        }
        if (binding.birthdayContainer?.error != null) {
            message += "\n${getString(R.string.birth_date)}: ${binding.birthdayContainer?.error}"
        }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.invalid_form))
            .setMessage(message)
            .setPositiveButton(getString(R.string.okay)) { _, _ ->
                // do nothing
            }
            .show()
    }
}