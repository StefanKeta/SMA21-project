package com.example.licenta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.model.food.Food

class AddFoodAdapter(
    private val ctx: Context,
    private val foodList: List<Food>
) : RecyclerView.Adapter<AddFoodAdapter.AddFoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFoodViewHolder {
        return AddFoodViewHolder(
            LayoutInflater
                .from(ctx)
                .inflate(
                    R.layout.add_food_view_holder,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: AddFoodViewHolder, position: Int) {
        holder.update(position)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class AddFoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val foodName: TextView = view.findViewById(R.id.add_food_view_holder_food_name_tv)
        private val foodCalories: TextView = view.findViewById(R.id.add_food_view_holder_food_kcal_tv)

        fun update(position: Int){
        }
    }
}