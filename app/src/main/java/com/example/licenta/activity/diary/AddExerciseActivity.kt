package com.example.licenta.activity.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.adapter.ExercisesAdapter
import com.example.licenta.contract.AddCustomExerciseContract
import com.example.licenta.firebase.db.ExercisesDB
import com.example.licenta.firebase.db.WeightExerciseRecordDB
import com.example.licenta.model.exercise.Exercise
import com.example.licenta.model.exercise.WeightExerciseRecord
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class AddExerciseActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {
    private lateinit var searchTIL: TextInputLayout
    private lateinit var searchET: TextInputEditText
    private lateinit var addCustomBtn : Button
    private lateinit var groupsTIL: TextInputLayout
    private lateinit var groupsACTV: AutoCompleteTextView
    private lateinit var exercisesRV: RecyclerView
    private lateinit var exercisesList: MutableList<Exercise>
    private lateinit var groupsList: MutableList<String>
    private lateinit var recordsList: MutableList<Double>
    private lateinit var addCustomExerciseContract: ActivityResultLauncher<Unit>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercise)
        initComponents()
    }

    private fun initComponents() {
        searchTIL = findViewById(R.id.activity_exercise_search_exercise_til)
        searchET = findViewById(R.id.activity_exercise_search_exercise_et)
        addCustomBtn = findViewById(R.id.activity_exercise_add_custom_btn)
        addCustomBtn.setOnClickListener(this)
        exercisesRV = findViewById(R.id.activity_exercise_exercises_rv)
        exercisesRV.layoutManager = LinearLayoutManager(this@AddExerciseActivity)
        exercisesRV.hasFixedSize()
        addCustomExerciseContract = registerForActivityResult(
            AddCustomExerciseContract(),
            ::handleResponseFromAddingCustomExercise
        )
        ExercisesDB.getAllExercises(::initGroups)
        val exercise = WeightExerciseRecord(
            UUID.randomUUID().toString(),
            exercisesList[0].id,
            4,
            8,
            30.0,
        )
        WeightExerciseRecordDB.saveRecord(exercise)
    }

    private fun initGroups(exercisesList: MutableList<Exercise>) {
        this.exercisesList = exercisesList
        groupsTIL = findViewById(R.id.activity_exercise_group_til)
        groupsACTV = findViewById(R.id.activity_exercise_group_actv)
        exercisesList
            .map { exercise -> exercise.group }
            .distinct()
            .toCollection(groupsList)
        val adapter = ArrayAdapter(
            this@AddExerciseActivity,
            android.R.layout.simple_spinner_dropdown_item,
            groupsList
        )
        groupsACTV.setAdapter(adapter)
        groupsACTV.onItemSelectedListener = this
    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.activity_exercise_add_custom_btn) launchAddingContract()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view!!.id == R.id.activity_exercise_group_actv) {
            val groupExercises = exercisesList
                .filter { exercise ->
                    exercise.group.lowercase() == parent!!.getItemAtPosition(position).toString()
                        .lowercase()
                }
                .toMutableList()
            populateExercises(groupExercises)
        }
        //get exercises for that specific group
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //reset the exercises and weight
    }

    private fun populateExercises(exercises: MutableList<Exercise>) {
        val adapter = ExercisesAdapter(this, exercises)
        exercisesRV.adapter = adapter
    }

    private fun launchAddingContract() {
        addCustomExerciseContract.launch(Unit)
    }

    private fun handleResponseFromAddingCustomExercise(isAdded: Boolean) {
        //refresh the list
    }
}