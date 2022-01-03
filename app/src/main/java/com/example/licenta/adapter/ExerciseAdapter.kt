package com.example.licenta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.firebase.db.ExercisesDB
import com.example.licenta.model.exercise.WeightExerciseRecord
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ExerciseAdapter(
    private val ctx: Context,
    options: FirestoreRecyclerOptions<WeightExerciseRecord>
) : FirestoreRecyclerAdapter<WeightExerciseRecord, ExerciseAdapter.ExerciseRecordViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseRecordViewHolder {
        return ExerciseRecordViewHolder(
            LayoutInflater
                .from(ctx)
                .inflate(
                    R.layout.item_holder_exercise_record,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(
        holder: ExerciseRecordViewHolder,
        position: Int,
        model: WeightExerciseRecord
    ) {
        holder.update(model)
    }

    inner class ExerciseRecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nameTV: TextView = view.findViewById(R.id.item_exercise_record_name_tv)
        private var groupTV: TextView = view.findViewById(R.id.item_exercise_record_group_tv)
        private var setsTV: TextView = view.findViewById(R.id.item_exercise_record_sets_tv)
        private var repsTV: TextView = view.findViewById(R.id.item_exercise_record_reps_tv)
        private var weightTV: TextView = view.findViewById(R.id.item_exercise_record_weight_tv)

        fun update(weightExerciseRecord: WeightExerciseRecord){
            ExercisesDB.getExerciseById(weightExerciseRecord.exerciseId){ exercise ->
                nameTV.text = exercise.name
                groupTV.text = exercise.group
                setsTV.text = weightExerciseRecord.sets.toString()
                repsTV.text = weightExerciseRecord.reps.toString()
                weightTV.text = weightExerciseRecord.weight.toString()
            }
        }
    }
}