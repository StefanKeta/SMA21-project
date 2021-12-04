package com.example.licenta.activity.diary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.activity.camera.ScanBarcodeActivity
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
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data!!.extras!!
                    handleResponseFromCamera(data)
                }
            }
        startForResult.launch(Intent(this@AddFoodActivity, ScanBarcodeActivity::class.java))
    }

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {
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

    private fun handleResponseFromCamera(data: Bundle) {
        val foodExists = data.getBoolean(ScanBarcodeActivity.BUNDLE_EXTRAS_EXISTS)
        val foodBarcode = data.getString(ScanBarcodeActivity.BUNDLE_EXTRAS_BARCODE)

        if (foodExists) {
            val intent = Intent(this@AddFoodActivity, FoodInfoActivity::class.java)
            intent.putExtra(ScanBarcodeActivity.BUNDLE_EXTRAS_BARCODE,foodBarcode)
            val goToFoodInfo =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                    {
                        //addFoodOrNot
                    }
                )
            goToFoodInfo.launch(intent)
        } else {
            val intent = Intent(this@AddFoodActivity, RegisterFoodToDbActivity::class.java)
            intent.putExtra(ScanBarcodeActivity.BUNDLE_EXTRAS_BARCODE,foodBarcode)
            val goToAddFoodToFirestore =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                    {

                    })
            goToAddFoodToFirestore.launch(intent)
        }
    }

    private fun setUpRecyclerView() {
        foodRV = findViewById(R.id.activity_add_food_rv)
        adapter = MealsFoodAdapter(this@AddFoodActivity, ArrayList<Food>())
        foodRV.adapter = adapter
    }
}