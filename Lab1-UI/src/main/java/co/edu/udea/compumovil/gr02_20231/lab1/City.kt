package co.edu.udea.compumovil.gr02_20231.lab1

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CitiesResponse(
    @Json(name = "error") val error: Boolean,
    @Json(name = "msg") val msg: String,
    @Json(name = "data") val data: List<String>
)