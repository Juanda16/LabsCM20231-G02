package co.edu.udea.compumovil.gr02_20231.lab1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.edu.udea.compumovil.gr02_20231.lab1.databinding.ActivityPersonalDataBinding




class PersonalDataActivity : AppCompatActivity() {



    private lateinit var binding: ActivityPersonalDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_personal_data)
        //setContentView(binding.root)

        val schoolGrade = resources.getStringArray(R.array.school_grade)
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item,
            schoolGrade
        )
        val textView: AutoCompleteTextView = findViewById(R.id.lista)
        textView.setAdapter(adapter)



        //binding.NextBtn.setOnClickListener{verifyForm()}
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
        val nameText = binding.Name.text.toString()
        if (nameText.isEmpty()) {
            return getString(R.string.required)
        }
        return null
    }
    private fun validateLastName(): String? {
        val nameText = binding.LastName.text.toString()
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