package co.edu.udea.compumovil.gr02_20231.lab1

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var cities: List<String> = listOf("Medellin", "Otra")
    lateinit var cities2: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val spinner = findViewById<Spinner>(R.id.spinner)

        lifecycleScope.launch(Dispatchers.IO) {
            cities2 = getCitiesByCountry("colombia").data

            
//            withContext(Dispatchers.Main) {
//            }
        }
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities2)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCity = parent.getItemAtPosition(position) as String
                Log.d("selected city", selectedCity)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // hacer algo cuando no se ha seleccionado nada
            }
        }


    }


    fun getCitiesByCountry(countryCode: String): CitiesResponse {
        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("country", countryCode)
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
                Log.d(TAG, responseBody)

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

@JsonClass(generateAdapter = true)
data class CitiesResponse(
    @Json(name = "error") val error: Boolean,
    @Json(name = "msg") val msg: String,
    @Json(name = "data") val data: List<String>
)
