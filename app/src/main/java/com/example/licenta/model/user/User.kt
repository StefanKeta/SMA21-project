package com.example.licenta.model.user

import java.sql.Timestamp

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val dob: Timestamp,
    val gender: Char,
    val height: Double,
    val weight: Double,
)