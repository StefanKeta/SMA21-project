package com.example.licenta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.model.exercise.Exercise

class ExercisesAdapter(
    private val context:Context,
    private val exercisesList: MutableList<Exercise>
) : RecyclerView.Adapter<ExercisesAdapter.ExercisesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
        return ExercisesViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.item_holder_exercise,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        holder.update(position)
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }


    inner class ExercisesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nameTV: TextView = view.findViewById(R.id.item_exercise_name_tv)
        private var recordTV: TextView = view.findViewById(R.id.item_exercise_record_tv)

        fun update(position: Int) {
            nameTV.text = exercisesList[position].name
        }
    }
}