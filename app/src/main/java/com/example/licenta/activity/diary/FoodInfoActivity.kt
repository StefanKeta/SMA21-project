package com.example.licenta.activity.diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.licenta.R
import com.example.licenta.data.LoggedUserData
import com.example.licenta.data.LoggedUserGoals
import com.example.licenta.firebase.Auth
import com.example.licenta.firebase.db.FoodDB
import com.example.licenta.firebase.db.SelectedFoodDB
import com.example.licenta.math.CalorieCalculator
import com.example.licenta.math.MacroCalculator
import com.example.licenta.model.food.Food
import com.example.licenta.model.food.FoodMeasureUnitEnum
import com.example.licenta.model.food.SelectedFood
import com.example.licenta.util.IntentConstants
import com.example.licenta.util.Util
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException
import java.util.*

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
    private lateinit var food: Food
    private lateinit var textChangedListener: TextWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_info)
        val id = intent.getStringExtra(Food.ID)
        FoodDB.getFoodById(id!!,::initValues)
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
        textChangedListener = initTextListener()
        quantityET.addTextChangedListener(textChangedListener)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.activity_food_info_back_btn -> startActivity(Intent(this@FoodInfoActivity,AddFoodActivity::class.java))
            R.id.activity_food_info_save_btn -> Unit // TODO: 05.12.2021  saveToUsersFoodForToday
        }
    }

    private fun initValues(food:Food){
        this.food = food
        nameTV.text = food.name
        proteinTV.text = food.protein.toString()
        carbsTV.text = food.carbs.toString()
        fatTV.text = food.fat.toString()
        caloriesTV.text = food.calories.toString()
        quantityET.setText(R.string.activity_food_info_100_grams)
    }

    private fun modifyETs(quantity:Double,measureUnit:FoodMeasureUnitEnum){
        val inGrams = if(measureUnit == FoodMeasureUnitEnum.OZ)
            Util.convertOzToG(quantity) else quantity
        val calories = food.calories * inGrams/100
        val protein = food.protein * inGrams/100
        val fat = food.fat * inGrams/100
        val carbs = food.carbs * inGrams/100
        proteinTV.text = protein.toString()
        carbsTV.text = carbs.toString()
        fatTV.text =  fat.toString()
        caloriesTV.text = calories.toString()
    }

    private fun saveSelectedFood(quantity: Double,measureUnit: FoodMeasureUnitEnum){
        val selectedFood = SelectedFood(
            UUID.randomUUID().toString(),
            food.id,
            LoggedUserData.getLoggedUser().uuid,
            quantity,
            measureUnit
        )

        SelectedFoodDB
            .saveSelectedFood(selectedFood,::savedFoodCallback)
    }

    private fun savedFoodCallback(isSaved:Boolean){
        val intent = Intent(this@FoodInfoActivity,AddFoodActivity::class.java)
        if(isSaved){
            intent.putExtra(IntentConstants.IS_SELECTED_FOOD_SAVED,true)
        }else{
            intent.putExtra(IntentConstants.IS_SELECTED_FOOD_SAVED,false)
        }
        setResult(RESULT_OK,intent)
    }

    private fun initTextListener():TextWatcher{
        return object :TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s!=null){
                val quantity = s.trim().toString().toDouble()
                val measureUnit = if(unitToggleGroup.checkedButtonId == R.id.activity_food_info_g_rb)
                    FoodMeasureUnitEnum.GRAM
                else
                    FoodMeasureUnitEnum.OZ
                modifyETs(quantity,measureUnit)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            //
        }

    }}

    override fun onStart() {
        super.onStart()
        if(Auth.currentUser()!=null || Auth.currentUser()!!.uid != LoggedUserData.getLoggedUser().uuid)
            throw RuntimeException("Unauthorized! You will be redirected to login page")
    }
}