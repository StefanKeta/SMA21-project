package com.example.licenta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.model.food.Food

class MealsFoodAdapter(
    private val ctx:Context,
    private val selectedFood: List<Food>
): RecyclerView.Adapter<MealsFoodAdapter.MealsFoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsFoodViewHolder {
        return MealsFoodViewHolder(
            LayoutInflater
                .from(ctx)
                .inflate(R.layout.meal_food_item_holder,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MealsFoodViewHolder, position: Int) {
        holder.update(position)
    }

    override fun getItemCount(): Int {
        return selectedFood.size
    }

    class MealsFoodViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val foodNameTV: TextView = view.findViewById(R.id.meal_food_item_holder_food_name_tv)
        private val foodQuantityTV: TextView = view.findViewById(R.id.meal_food_item_holder_quantity_tv)
        private val foodMeasureUnit: TextView = view.findViewById(R.id.meal_food_item_holder_weight_unit_tv)
        private val foodCaloriesTV: TextView = view.findViewById(R.id.meal_food_item_holder_kcal_tv)

        fun update(position:Int){

        }
    }
}