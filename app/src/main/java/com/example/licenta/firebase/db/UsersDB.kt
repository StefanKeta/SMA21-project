package com.example.licenta.firebase.db

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.licenta.activity.auth.LoginActivity
import com.example.licenta.data.LoggedUserData
import com.example.licenta.model.user.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object UsersDB {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun addUser(user: User, ctx: Context) {
        db.collection(CollectionsName.USERS)
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(
                    ctx,
                    "User added!",
                    Toast.LENGTH_SHORT
                )
                    .show()
                ctx.startActivity(Intent(ctx, LoginActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(ctx, it.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun getUser(id: String, callback: (User?) -> Unit) {
        db.collection(CollectionsName.USERS)
            .whereEqualTo(User.USER_UUID, id)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result!!.documents.size == 1)
                        callback(it.result!!.documents[0].toObject(User::class.java))
                } else
                    callback(null)
            }
    }
}