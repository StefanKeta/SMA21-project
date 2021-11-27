package com.example.licenta.firebase.db

import com.example.licenta.model.user.Goals
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.RuntimeException

object GoalsDB {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun userHasGoals(id: String, callback: (Boolean) -> Unit) {
        db.collection(CollectionsName.GOALS)
            .whereEqualTo(Goals.USER_ID, id)
            .get()
            .addOnCompleteListener {
                val documents = it.result?.documents
                if (documents != null) {
                    if (documents.size == 1)
                        callback(true)
                    else
                        callback(false)
                } else throw NoSuchElementException("User does not exist!")
            }
            .addOnFailureListener {
                throw RuntimeException("Something went wrong!")
            }
    }

    fun addUserGoals(goals: Goals, callback: (Boolean) -> Unit) {
        db.collection(CollectionsName.GOALS)
            .add(goals)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true)
                } else
                    callback(false)
            }
    }
}