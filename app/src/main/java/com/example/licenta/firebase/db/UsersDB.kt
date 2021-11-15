package com.example.licenta.firebase.db

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.licenta.activity.auth.LoginActivity
import com.example.licenta.model.user.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UsersDB {
    private val db by lazy {
        Firebase.firestore
    }
    private val collectionReference = db.collection(CollectionsName.USERS)

    fun addUserToDB(user: User,ctx: Context) {
        db.collection(CollectionsName.USERS)
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(
                    ctx,
                    "User added!",
                    Toast.LENGTH_SHORT
                )
                    .show()
                ctx.startActivity(Intent(ctx,LoginActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(ctx,"${it.toString()}",Toast.LENGTH_SHORT)
                    .show()
            }
    }


}