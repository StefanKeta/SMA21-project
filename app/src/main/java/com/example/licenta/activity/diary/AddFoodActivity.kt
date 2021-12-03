package com.example.licenta.activity.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.adapter.MealsFoodAdapter
import com.example.licenta.model.food.Food
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddFoodActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var searchFoodLayout: TextInputLayout
    private lateinit var searchFoodET: TextInputEditText
    private lateinit var barcodeIV: ImageView
    private lateinit var foodRV: RecyclerView
    private lateinit var adapter: MealsFoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)
        initComponents()
    }

    private fun initComponents() {
        searchFoodLayout = findViewById(R.id.activity_add_food_search_for_food_til)
        searchFoodET = findViewById(R.id.activity_add_food_search_for_food_et)
        barcodeIV = findViewById(R.id.activity_add_food_barcode_iv)
        barcodeIV.setOnClickListener(this)
        searchFoodET.addTextChangedListener(textWatcher())
        setUpRecyclerView()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.activity_add_food_barcode_iv -> openCameraToScan()
        }
    }

    private fun openCameraToScan() {

    }

    private fun textWatcher():TextWatcher{
        return object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setUpRecyclerView(){
        foodRV = findViewById(R.id.activity_add_food_rv)
        adapter = MealsFoodAdapter(this@AddFoodActivity,ArrayList<Food>())
        foodRV.adapter = adapter
    }
}