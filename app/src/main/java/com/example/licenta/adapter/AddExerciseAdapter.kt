package com.example.licenta.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.model.exercise.Exercise

class AddExerciseAdapter(
    private val context: Context,
    private val exercisesList: MutableList<Exercise>,
    private val onExerciseItemClickListener: OnExerciseItemClickListener
) : RecyclerView.Adapter<AddExerciseAdapter.ExercisesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
        return ExercisesViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.item_holder_exercise,
                    parent,
                    false
                ),
            onExerciseItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        holder.update(position)
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }


    inner class ExercisesViewHolder(
        view: View,
        private val onExerciseItemClickListener: OnExerciseItemClickListener
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var nameTV: TextView = view.findViewById(R.id.item_exercise_name_tv)
        private var recordTV: TextView = view.findViewById(R.id.item_exercise_record_tv)

        init {
            view.setOnClickListener(this)
        }

        fun update(position: Int) {
            nameTV.text = exercisesList[position].name
        }

        override fun onClick(view: View?) {
            Log.d("addExercise", "onExerciseItemClick: $context")
            val id = exercisesList[absoluteAdapterPosition].id
            onExerciseItemClickListener.onExerciseItemClick(id)
        }
    }

    interface OnExerciseItemClickListener {
        fun onExerciseItemClick(exerciseId:String)
    }
}