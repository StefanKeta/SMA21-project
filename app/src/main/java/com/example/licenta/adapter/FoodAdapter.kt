package com.example.licenta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.firebase.db.FoodDB
import com.example.licenta.model.food.FoodMeasureUnitEnum
import com.example.licenta.model.food.SelectedFood
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class FoodAdapter(
    private val ctx: Context,
    optionsQuery: FirestoreRecyclerOptions<SelectedFood>
) : FirestoreRecyclerAdapter<SelectedFood, FoodAdapter.FoodHolder>(optionsQuery) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        return FoodHolder(
            LayoutInflater
                .from(ctx)
                .inflate(
                    R.layout.meal_food_item_holder,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int, selectedFood: SelectedFood) {
        holder.update(selectedFood)
    }

    inner class FoodHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nameTV:TextView = view.findViewById(R.id.meal_food_item_holder_food_name_tv)
        private var quantityTV:TextView = view.findViewById(R.id.meal_food_item_holder_quantity_tv)
        private var unitTV:TextView = view.findViewById(R.id.meal_food_item_holder_weight_unit_tv)
        private var kcalTV:TextView = view.findViewById(R.id.meal_food_item_holder_kcal_tv)

        fun update(selectedFood: SelectedFood){
            FoodDB.getFoodById(selectedFood.foodId){ food ->
                nameTV.text = food.name
                quantityTV.text = (selectedFood.quantity*100.0).toInt().toString()
                unitTV.text = if (selectedFood.unit == FoodMeasureUnitEnum.GRAM) "g" else "oz"
                kcalTV.text = (selectedFood.quantity * food.calories).toInt().toString()
            }
        }
    }
}