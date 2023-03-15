package co.edu.udea.compumovil.gr02_20231.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import co.edu.udea.compumovil.gr02_20231.lab1.databinding.ActivityContactDataBinding

class ContactDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_data)

        binding = ActivityContactDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        phoneFocusListener()
        emailFocusListener()
        countryFocusListener()

        binding.submitButton.setOnClickListener { submitForm() }

    }

    private fun submitForm() {
        binding.phoneContainer.helperText = validatePhone()
        binding.emailContainer.helperText = validateEmail()
        binding.countryContainer.helperText = validateCountry()

        val valid = binding.phoneContainer.helperText == null &&
                binding.emailContainer.helperText == null &&
                binding.countryContainer.helperText == null


        if (valid) {
            resetForm()
        } else {
            invalidForm()
        }

    }

    private fun resetForm() {

        /*
        var intent: Intent = intent
        val fullName = intent.getStringExtra("fullName")
        val gender = intent.getStringExtra("gender")
        val birthDate = intent.getStringExtra("birthDate")
        val schoolGrade = intent.getStringExtra("schoolGrade")

        var message = "Full name: $fullName"
        if (gender != null) {
            message += "\nGender: $gender"
        }
        message += "\nBirthdate: $birthDate"
        if (schoolGrade != null) {
            message += "\nSchool level: $schoolGrade"
        }
        */


        var message = "Phone: ${binding.phoneEditText.text}"
        message += "\nEmail: ${binding.emailEditText.text}"
        message += "\nCountry: ${binding.countryEditText.text}"
        if (binding.cityEditText.text.isNotEmpty())
            message += "\nCity: ${binding.cityEditText.text}"
        if (binding.addressEditText.text?.isNotEmpty() == true)
            message += "\nAddress: ${binding.addressEditText.text}"
        AlertDialog.Builder(this)
            .setTitle("Form Submitted")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                binding.phoneEditText.text?.clear()
                binding.emailEditText.text?.clear()
                binding.countryEditText.text?.clear()
                binding.cityEditText.text?.clear()
                binding.addressEditText.text?.clear()

                binding.phoneContainer.helperText = getString(R.string.required)
                binding.emailContainer.helperText = getString(R.string.required)
                binding.countryContainer.helperText = getString(R.string.required)

            }
            .show()
        showLog()

    }

    private fun showLog() {
/*
        Log.d("personInfo", "Personal information:")
        Log.d("personInfo", "Full name: ${binding.fullNameEditText.text}")
        Log.d("personInfo", "Gender: ${binding.genderEditText.text}")
        Log.d("personInfo", "Birthdate: ${binding.birthdateEditText.text}")
        Log.d("personInfo", "School level: ${binding.schoolLevelEditText.text}")

*/
        Log.d("contactInfo", "Contact information:")
        Log.d("contactInfo", "Phone: ${binding.phoneEditText.text}")
        Log.d("contactInfo", "Email: ${binding.emailEditText.text}")
        Log.d("contactInfo", "Country: ${binding.countryEditText.text}")
        if (binding.cityEditText.text.isNotEmpty())
            Log.d("contactInfo", "${R.string.city}: ${binding.cityEditText.text}")
        if (binding.addressEditText.text?.isNotEmpty() == true)
            Log.d("contactInfo", "Address: ${binding.addressEditText.text}")
    }


    private fun invalidForm() {
        var message = ""
        if (binding.phoneContainer.helperText != null) {
            message += "Phone: ${binding.phoneContainer.helperText}"
        }
        if (binding.emailContainer.helperText != null) {
            message += "\nEmail: ${binding.emailContainer.helperText}"
        }
        if (binding.countryContainer.helperText != null) {
            message += "\nCountry: ${binding.countryContainer.helperText}"
        }
        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                // do nothing
            }
            .show()


    }


    private fun validatePhone(): String? {
        val phoneText = binding.phoneEditText.text.toString()
        if (phoneText.isEmpty()) {
            return getString(R.string.required)
        }
        if (!phoneText.matches(".*[0-9].*".toRegex())) {
            return "Must be all Digits"
        }
        //bettwen 4 and 12 digits
        if (phoneText.length < 4 || phoneText.length > 12) {
            return "Must be between 4 and 12 digits"
        }
        return null
    }

    private fun phoneFocusListener() {
        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneContainer.helperText = validatePhone()
            }
        }
    }

    private fun validateEmail(): String? {
        val emailText = binding.emailEditText.text.toString()
        if (emailText.isEmpty()) {
            return getString(R.string.required)
        }
        if (!emailText.matches(".*@.*".toRegex())) {
            return "Must be a valid email"
        }
        return null
    }

    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainer.helperText = validateEmail()
            }
        }
    }

    private fun validateCountry(): String? {
        val countryText = binding.countryEditText.text.toString()
        if (countryText.isEmpty()) {
            return getString(R.string.required)
        }
        if (binding.countryEditText.text.toString().lowercase() == "colombia") {
            //TODO get cities from API https://countriesnow.space/
            val cities = listOf(
                "Bogotá",
                "Medellín",
                "Cali",
                "Barranquilla",
                "Cartagena",
                "Cúcuta",
                "Bucaramanga",
                "Soledad",
                "Santa Marta",
                "Ibagué",
                "Pereira",
                "Manizales",
                "Bello",
                "Neiva",
                "Pasto",
                "Armenia",
                "Villavicencio",
                "Buenaventura",
                "Valledupar",
            )
            val adapterCity = ArrayAdapter(
                this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                cities
            )
            binding.cityEditText.setAdapter(adapterCity)
        }
        return null
    }

    private fun countryFocusListener() {
        //Autocomplete countries
        //Example TODO get from API https://countriesnow.space/
        val countries = listOf(
            "Argentina",
            "Bolivia",
            "Brasil",
            "Chile",
            "Colombia",
            "Costa Rica",
            "Cuba",
            "Ecuador",
            "El Salvador",
            "Guatemala",
            "Honduras",
            "México",
            "Nicaragua",
            "Panamá",
            "Paraguay",
            "Perú",
            "Puerto Rico",
            "República Dominicana",
            "Uruguay",
            "Venezuela"
        )
        val adapterCity = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            countries
        )
        binding.countryEditText.setAdapter(adapterCity)

        binding.countryEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.countryContainer.helperText = validateCountry()
            }
        }
    }

}