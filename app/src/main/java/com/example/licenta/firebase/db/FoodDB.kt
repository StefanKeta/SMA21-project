package com.example.licenta.firebase.db

import android.util.Log
import com.example.licenta.model.food.Food
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList

object FoodDB {
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getInitialFoodList(limit: Long, callback: (List<Food>) -> Unit) {
        db
                .collection(CollectionsName.FOOD)
                .limit(limit)
                .get()
                .addOnCompleteListener { snapshot ->
                    if (snapshot.isSuccessful) {
                        val results = ArrayList<Food>()
                        val documents = snapshot.result.documents
                        for (document in documents) {
                            val food = document.toObject(Food::class.java)
                            if (food != null)
                                results.add(food)
                        }
                    } else {
                        throw RuntimeException("Oops!Something went wrong")
                    }
                }
    }

    fun initialAddFoodAdapterList(limit: Long = 20): FirestoreRecyclerOptions<Food> {
        val query = db
                .collection(CollectionsName.FOOD)
                .orderBy(Food.NAME, Query.Direction.DESCENDING)
                .limit(limit)

        return FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(query, Food::class.java)
                .build()
    }


    fun searchForFoodByNamePrefix(nameToMatch: String): FirestoreRecyclerOptions<Food> {
        val query = db
                .collection(CollectionsName.FOOD)
                .whereGreaterThanOrEqualTo(Food.NAME,
                        nameToMatch.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                .whereLessThanOrEqualTo(Food.NAME,
                        "$nameToMatch~".replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                .orderBy(Food.NAME, Query.Direction.ASCENDING)

        return FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(query, Food::class.java)
                .build()
    }


    fun foodExists(barcodeRawValue: String, existsCallback: (Boolean, String) -> Unit) {
        db
                .collection(CollectionsName.FOOD)
                .whereEqualTo(Food.BARCODE, barcodeRawValue)
                .get()
                .addOnCompleteListener { snapshotResult ->
                    Log.d("foodExists", "Searching...")
                    if (snapshotResult.isSuccessful) {
                        val documents = snapshotResult.result.documents
                        if (documents.isEmpty()) {
                            existsCallback(false, "")
                            Log.d("foodExists", "Empty...")
                        } else {
                            val foodID = documents[0]
                                    .toObject(Food::class.java)
                                    ?.id ?: ""
                            Log.d("foodExists", "$foodID")
                            existsCallback(true, foodID)
                        }
                    } else {
                        throw RuntimeException("Oops! Something went wrong!")
                    }
                }
    }

    fun addFood(food: Food, isAdded: (Boolean) -> Unit) {
        if (food.barcode.isEmpty()) {
            db
                    .collection(CollectionsName.FOOD)
                    .add(food)
                    .addOnCompleteListener { documentRef ->
                        if (documentRef.isSuccessful) {
                            isAdded(true)
                        } else {
                            isAdded(false)
                        }
                    }
        } else {
            db.collection(CollectionsName.FOOD)
                    .whereEqualTo(Food.BARCODE, food.barcode)
                    .get()
                    .addOnCompleteListener { snapshot ->
                        if (snapshot.isSuccessful) {
                            val documents = snapshot.result.documents
                            if (documents.isEmpty()) {
                                db
                                        .collection(CollectionsName.FOOD)
                                        .add(food)
                                        .addOnCompleteListener { documentRef ->
                                            if (documentRef.isSuccessful) {
                                                isAdded(true)
                                            } else {
                                                isAdded(false)
                                            }
                                        }
                            } else {
                                isAdded(false)
                            }
                        } else {
                            throw RuntimeException("Failed to retrieve documents!")
                        }
                    }
        }

    }

}