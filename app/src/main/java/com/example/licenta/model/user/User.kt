package com.example.licenta.model.user


data class User(
    val firstName: String,
    val lastName: String,
    val dob: Long,
    val gender: String,
    val height: Int,
    val weight: Int,
) {
    companion object {
        const val FIRST_NAME = "firstName"
        const val LAST_NAME = "lastName"
        const val DATE_OF_BIRTH = "dob"
        const val GENDER = "gender"
        const val HEIGHT = "height"
        const val WEIGHT = "weight"
    }
}