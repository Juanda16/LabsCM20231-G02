package co.edu.udea.compumovil.gr02_20231.lab1.model

import java.io.Serializable

private const val TAG = "Person"

class Person(
    var Name: String,
    var lastName: String,
    var gender: String? = null,
    var birthday: String,
    var educationLevel: String? = null,
): Serializable {
    // your stuff
}

