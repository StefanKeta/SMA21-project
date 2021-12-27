package com.example.licenta.firebase.db

import com.example.licenta.model.exercise.Exercise
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.RuntimeException

object ExercisesDB {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getAllExercises(callback: (MutableList<Exercise>)->Unit){
        db
            .collection(CollectionsName.EXERCISE)
            .get()
            .addOnCompleteListener{snapshot ->
                if(snapshot.isSuccessful){
                    val documents = snapshot.result.documents
                    val exerciseList: MutableList<Exercise> = ArrayList()
                    for(document in documents)
                        document.toObject(Exercise::class.java)?.let { exerciseList.add(it) }
                }else{
                    throw RuntimeException("Oops! Something went wrong")
                }
            }
    }

    fun saveAnExerciseToDB(exercise: Exercise){
        db
            .collection(CollectionsName.EXERCISE)
            .add(exercise)
            .addOnFailureListener { it ->
                it.printStackTrace()
            }
    }

    fun getExercisesByGroup(group: String,callback: (MutableList<Exercise>)->Unit){
        db
            .collection(CollectionsName.EXERCISE)
            .whereEqualTo(Exercise.GROUP,group)
            .get()
            .addOnCompleteListener { result ->
                if(result.isSuccessful){
                    val documents = result.result.documents
                    val exercises: MutableList<Exercise> = ArrayList()
                    for(document in documents){
                        exercises.add(document.toObject(Exercise::class.java)!!)
                    }
                    callback(exercises)
                }else
                    throw RuntimeException("Oops!Something went wrong")
            }
    }
}