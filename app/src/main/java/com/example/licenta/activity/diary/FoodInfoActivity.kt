package com.example.licenta.activity.diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.licenta.R
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FoodInfoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var nameTV: TextView
    private lateinit var quantityTIL: TextInputLayout
    private lateinit var quantityET: TextInputEditText
    private lateinit var unitToggleGroup: MaterialButtonToggleGroup
    private lateinit var caloriesTV : TextView
    private lateinit var proteinTV: TextView
    private lateinit var carbsTV: TextView
    private lateinit var fatTV: TextView
    private lateinit var backBtn: Button
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_info)
        initComponents()
    }

    private fun initComponents(){
        nameTV = findViewById(R.id.activity_food_info_name_tv)
        quantityTIL = findViewById(R.id.activity_food_info_quantity_til)
        quantityET = findViewById(R.id.activity_food_info_quantity_et)
        unitToggleGroup = findViewById(R.id.activity_food_info_quantity_tbg)
        proteinTV = findViewById(R.id.activity_food_info_protein_tv)
        carbsTV = findViewById(R.id.activity_food_info_carbs_tv)
        caloriesTV = findViewById(R.id.activity_food_info_calories_tv)
        fatTV = findViewById(R.id.activity_food_info_fat_tv)
        backBtn = findViewById(R.id.activity_food_info_back_btn)
        backBtn.setOnClickListener(this)
        saveBtn = findViewById(R.id.activity_food_info_save_btn)
        saveBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.activity_food_info_back_btn -> startActivity(Intent(this@FoodInfoActivity,AddFoodActivity::class.java))
            R.id.activity_food_info_save_btn -> Unit // TODO: 05.12.2021  saveToUsersFoodForToday
        }
    }
}