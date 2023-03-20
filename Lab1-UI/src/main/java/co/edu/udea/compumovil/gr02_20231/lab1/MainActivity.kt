package co.edu.udea.compumovil.gr02_20231.lab1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import co.edu.udea.compumovil.gr02_20231.lab1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    var cities: List<String> = listOf("Medellin", "Otra")
    lateinit var cities2: List<String>

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.personal_data_activity)

        val schoolGrade = resources.getStringArray(R.array.school_grade)
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item,
            schoolGrade
        )
        val textView: AutoCompleteTextView = findViewById(R.id.lista)
        textView.setAdapter(adapter)


    }


    fun sendMessage(view: View) {
        val intent = Intent(this@MainActivity, ContactDataActivity::class.java)
        startActivity(intent)
    }


}


