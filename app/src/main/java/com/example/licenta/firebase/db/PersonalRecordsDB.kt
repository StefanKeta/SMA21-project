package com.example.licenta.firebase.db

import android.util.Log
import com.example.licenta.data.LoggedUserData
import com.example.licenta.model.exercise.PersonalRecord
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList

object PersonalRecordsDB {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getAllRecords(callback: (MutableList<PersonalRecord>) -> Unit) {
        db
            .collection(CollectionsName.PERSONAL_RECORDS)
            .whereEqualTo(PersonalRecord.USER_ID, LoggedUserData.getLoggedUser().uuid)
            .get()
            .addOnCompleteListener { snapshot ->
                if (snapshot.isSuccessful) {
                    val documents = snapshot.result.documents
                    val records: MutableList<PersonalRecord> = ArrayList()
                    for (document in documents)
                        document.toObject(PersonalRecord::class.java)
                            .also {
                                if (it != null) {
                                    records.add(it)
                                }
                                else
                                    throw RuntimeException("Oops! Could not parse personal record document to an object!")
                            }
                    callback(records)
                }
            }
    }

    fun checkIfRecord(exerciseId: String, currentWeight: Double, callback: (Boolean) -> Unit) {
        db
            .collection(CollectionsName.PERSONAL_RECORDS)
            .whereEqualTo(PersonalRecord.EXERCISE_ID, exerciseId)
            .whereEqualTo(PersonalRecord.USER_ID,LoggedUserData.getLoggedUser().uuid)
            .get()
            .addOnCompleteListener { snapshot ->
                val documents = snapshot.result.documents
                if (documents.size != 0) {
                    val document = documents[0]
                    document.toObject(PersonalRecord::class.java)
                        .also {
                            if (it != null) {
                                if (currentWeight == it.record)
                                    callback(true)
                                else
                                    callback(false)
                            } else
                                throw RuntimeException("Parser error")
                        }
                }
            }

    }

    fun checkAndUpdateRecordIfNeeded(
        exerciseId: String,
        weight: Double
    ) {
        db
            .collection(CollectionsName.PERSONAL_RECORDS)
            .whereEqualTo(PersonalRecord.EXERCISE_ID, exerciseId)
            .whereEqualTo(PersonalRecord.USER_ID,LoggedUserData.getLoggedUser().uuid)
            .get()
            .addOnCompleteListener { snapshot ->
                if (snapshot.isSuccessful) {
                    val documents = snapshot.result.documents
                    if (documents.size != 0)
                        snapshot.result.documents[0]
                            .also {
                                val record = it.toObject(PersonalRecord::class.java)
                                if (record != null) {
                                    val weightRecord = record.record
                                    if (weightRecord < weight) {
                                        updateRecord(record.id, weight)
                                    }
                                }
                            }
                    else{
                        addRecord(exerciseId,weight)
                    }
                }
            }
    }

    private fun updateRecord(recordId: String, weight: Double) {
        db
            .collection(CollectionsName.PERSONAL_RECORDS)
            .document(recordId)
            .update(PersonalRecord.RECORD, weight)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    throw RuntimeException("Could not update the record")
                }
            }
    }

    private fun addRecord(exerciseId: String, weight: Double){
        val record = PersonalRecord(
            UUID.randomUUID().toString(),
            exerciseId,
            LoggedUserData.getLoggedUser().uuid,
            weight
        )
        db
            .collection(CollectionsName.PERSONAL_RECORDS)
            .add(record)
            .addOnCompleteListener { ref ->
                if(!ref.isSuccessful)
                    throw RuntimeException("Could not add record to db!")
            }
    }

}