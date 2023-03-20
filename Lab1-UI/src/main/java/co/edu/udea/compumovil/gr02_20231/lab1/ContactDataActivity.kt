package co.edu.udea.compumovil.gr02_20231.lab1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.edu.udea.compumovil.gr02_20231.lab1.databinding.ActivityContactDataBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class ContactDataActivity : AppCompatActivity() {

    private val cities = mutableListOf(
        "Bogotá",
        "Medellín",

        )

    private val countries = listOf(
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

    private lateinit var adapterCity: ArrayAdapter<String>
    private lateinit var adapterCountry: ArrayAdapter<String>

    private lateinit var binding: ActivityContactDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_contact_data)
        binding = ActivityContactDataBinding.inflate(layoutInflater)

        adapterCountry = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            countries
        )

        adapterCity = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            cities
        )

        setContentView(binding.root)
        phoneFocusListener()
        emailFocusListener()
        countryFocusListener()

        binding.countryEditText.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = adapterCountry.getItem(position)
            // Perform action with the selected item
            Log.d("Test 2", ">>$selectedItem")
            createCoroutine(selectedItem ?: "colombia")
        }
        binding.cityEditText.setAdapter(adapterCity)
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

        var message = "${getString(R.string.phone)}: ${binding.phoneEditText.text}"
        message += "\n${getString(R.string.email)}: ${binding.emailEditText.text}"
        message += "\n${getString(R.string.country)}: ${binding.countryEditText.text}"
        if (binding.cityEditText.text.isNotEmpty())
            message += "\n${getString(R.string.city)}: ${binding.cityEditText.text}"
        if (binding.addressEditText.text?.isNotEmpty() == true)
            message += "\n${getString(R.string.address)}: ${binding.addressEditText.text}"


        AlertDialog.Builder(this)
            .setTitle(getString(R.string.form_submitted))
            .setMessage(message)
            .setPositiveButton(getString(R.string.okay)) { _, _ ->
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

    @SuppressLint("SuspiciousIndentation")
    private fun showLog() {
/*
        Log.d("personInfo", "Personal information:")
        Log.d("personInfo", "Full name: ${binding.fullNameEditText.text}")
        Log.d("personInfo", "Gender: ${binding.genderEditText.text}")
        Log.d("personInfo", "Birthdate: ${binding.birthdateEditText.text}")
        Log.d("personInfo", "School level: ${binding.schoolLevelEditText.text}")

*/

        Log.d("contactInfo", "${getString(R.string.contact_information)}:")
        Log.d("contactInfo", "${getString(R.string.phone)}: ${binding.phoneEditText.text}")
        Log.d("contactInfo", "${getString(R.string.email)}: ${binding.emailEditText.text}")
        Log.d("contactInfo", "${getString(R.string.country)}: ${binding.countryEditText.text}")
        if (binding.cityEditText.text.isNotEmpty()) {
            Log.d("contactInfo", "${getString(R.string.city)}: ${binding.cityEditText.text}")
        }
        if (binding.addressEditText.text?.isNotEmpty() == true) {
            Log.d("contactInfo", "${getString(R.string.address)}: ${binding.addressEditText.text}")
        }

    }


    private fun invalidForm() {
        var message = ""
        if (binding.phoneContainer.helperText != null) {
            message += "${R.string.phone}: ${binding.phoneContainer.helperText}"
        }
        if (binding.emailContainer.helperText != null) {
            message += "\n${R.string.email}: ${binding.emailContainer.helperText}"
        }
        if (binding.countryContainer.helperText != null) {
            message += "\n${R.string.country}: ${binding.countryContainer.helperText}"
        }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.invalid_form))
            .setMessage(message)
            .setPositiveButton(getString(R.string.okay)) { _, _ ->
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
            return getString(R.string.must_be_a_number)
        }
        //bettwen 4 and 12 digits
        if (phoneText.length < 4 || phoneText.length > 12) {
            return getString(R.string.must_be_between_4_and_12_digits)
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
            return getString(R.string.must_be_a_valid_email)
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


        }
        return null
    }

    private fun countryFocusListener() {
        //Autocomplete countries
        //Example TODO get from API https://countriesnow.space/


        binding.countryEditText.setAdapter(adapterCountry)


        binding.countryEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.countryContainer.helperText = validateCountry()
            }
        }
    }

    private fun createCoroutine(countryCode: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val cities2 = getCitiesByCountry("colombia").data
            cities.clear()
            cities.addAll(cities2)

            withContext(Dispatchers.Main) {
                adapterCity.notifyDataSetChanged()
            }
        }
    }

    fun getCitiesByCountry(countryCode: String): CitiesResponse {
        val country = countryCode.lowercase()
        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("country", country)
            .build()

        val request = Request.Builder()
            .url("https://countriesnow.space/api/v0.1/countries/cities")
            .post(requestBody)
            .build()

        try {
            val response = client.newCall(request).execute()
            Log.d("RESPONSE", "$response")
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }

            val responseBody = response.body?.string()
            if (responseBody != null) {
                Log.d(ContentValues.TAG, responseBody)

                val gson = Gson()
                val apiResponse = gson.fromJson(responseBody, CitiesResponse::class.java)
                Log.d("Object1", apiResponse.data[0])


                return apiResponse ?: CitiesResponse(data = emptyList(), error = true, msg = "")

            } else
                return CitiesResponse(data = emptyList(), error = true, msg = "")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return CitiesResponse(data = emptyList(), error = true, msg = "")
    }

}