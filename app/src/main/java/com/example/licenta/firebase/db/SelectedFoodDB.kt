package com.example.licenta.firebase.db

import com.example.licenta.model.food.SelectedFood
import com.google.firebase.firestore.FirebaseFirestore

object SelectedFoodDB {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun saveSelectedFood(selectedFood:SelectedFood,callback:(Boolean)->Unit){
        db
            .collection(CollectionsName.SELECTED_FOOD)
            .add(selectedFood)
            .addOnCompleteListener { reference ->
                if(reference.isSuccessful){
                    callback(true)
                }else{
                    callback(false)
                }
            }
    }
}