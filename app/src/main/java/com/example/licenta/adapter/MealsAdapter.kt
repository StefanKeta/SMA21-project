package com.example.licenta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.model.food.Food
import com.example.licenta.model.food.Meal

class MealsAdapter(
    private val ctx: Context,
    private val mealList: List<Meal>
) : RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(
            LayoutInflater.from(ctx)
                .inflate(R.layout.meal_item_holder, parent, false),
            ctx
        )
    }


    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class MealsViewHolder(val view: View, ctx: Context) : RecyclerView.ViewHolder(view) {
        private var mealNo: TextView = view.findViewById(R.id.meal_item_meal_no_tv)
        private var foodRV: RecyclerView = view.findViewById(R.id.meal_item_food_rv)
        private var addFoodBtn: Button = view.findViewById(R.id.meal_item_add_btn)
        private var removeFoodBtn: Button = view.findViewById(R.id.meal_item_remove_btn)

        fun update(position: Int) {
            mealNo.text = "Meal ${position + 1}"
        }

    }


}