package co.edu.udea.compumovil.gr02_20231.lab1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this@MainActivity, PersonalDataActivity::class.java)
        startActivity(intent)

    }
}




