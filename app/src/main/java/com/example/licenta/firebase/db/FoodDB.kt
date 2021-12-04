package com.example.licenta.firebase.db

import com.example.licenta.model.food.Food
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import java.lang.RuntimeException

object FoodDB {
    private val db : FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun foodExists(barcodeRawValue: String,callback: (Boolean) -> Unit){
        db
            .collection(CollectionsName.FOOD)
            .whereEqualTo(Food.BARCODE,barcodeRawValue)
            .get()
            .addOnCompleteListener{snapshotResult ->
                if(snapshotResult.isSuccessful){
                    val documents = snapshotResult.result.documents
                    if(documents.isEmpty()){
                        callback(false)
                    }else{
                        callback(true)
                    }
                }else{
                    throw RuntimeException("Oops! Something went wrong!")
                }
            }
    }
}