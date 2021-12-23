package com.example.licenta.firebase.db

import android.util.Log
import com.example.licenta.firebase.Auth
import com.example.licenta.model.food.Food
import com.example.licenta.model.food.SelectedFood
import com.example.licenta.util.Date
import com.example.licenta.util.IntentConstants
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import java.lang.RuntimeException

object SelectedFoodDB {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun saveSelectedFood(selectedFood: SelectedFood, callback: (Boolean) -> Unit) {
        db
            .collection(CollectionsName.SELECTED_FOOD)
            .add(selectedFood)
            .addOnCompleteListener { reference ->
                if (reference.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

    fun getSelectedFoodByDate(date: String, callback: (List<SelectedFood>) -> Unit) {
        db
            .collection(CollectionsName.SELECTED_FOOD)
            .whereEqualTo(SelectedFood.DATE_SELECTED, date)
            .get()
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    val documents = result.result.documents
                    val selectedFoods =
                        documents
                            .map { documentSnapshot -> documentSnapshot?.toObject(SelectedFood::class.java) }
                            .map { foodNullable ->
                                foodNullable
                                    ?: throw RuntimeException("Could not parse the object!")
                            }
                    callback(selectedFoods)
                } else {
                    throw RuntimeException("Ooops! Something went wrong!")
                }
            }
    }

    fun getSelectedFoodsOption(date: String): FirestoreRecyclerOptions<SelectedFood> {
        val query = db
            .collection(CollectionsName.SELECTED_FOOD)
            .whereEqualTo(SelectedFood.DATE_SELECTED, date)

        return FirestoreRecyclerOptions.Builder<SelectedFood>()
            .setQuery(query, SelectedFood::class.java)
            .build()
    }
}