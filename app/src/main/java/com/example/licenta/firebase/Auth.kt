package com.example.licenta.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.licenta.R
import com.example.licenta.firebase.db.UsersDB
import com.example.licenta.model.user.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import java.sql.Timestamp

object Auth {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun registerUser(
        context: Context,
        user: User,
        email: String,
        password: String
    ) {
        auth
            .createUserWithEmailAndPassword(
                email,
                password
            )
            .addOnSuccessListener {
                UsersDB
                    .addUserToDB(user, context)
            }
            .addOnFailureListener {
                if(it is FirebaseAuthUserCollisionException)
                    Toast.makeText(context,context.getString(R.string.register_activity_auth_exception_email_exists),Toast.LENGTH_SHORT)
                        .show()
            }
    }
}