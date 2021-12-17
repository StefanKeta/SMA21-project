package com.example.licenta.activity.diary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.activity.auth.LoginActivity
import com.example.licenta.activity.camera.ScanBarcodeActivity
import com.example.licenta.adapter.AddFoodAdapter
import com.example.licenta.contract.BarcodeScannerContract
import com.example.licenta.contract.FoodInfoContract
import com.example.licenta.contract.RegisterFoodContract
import com.example.licenta.firebase.db.FoodDB
import com.example.licenta.model.food.Food
import com.example.licenta.util.IntentConstants
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class AddFoodActivity : AppCompatActivity(), View.OnClickListener,
    AddFoodAdapter.OnAddFoodItemClickListener {
    private lateinit var searchFoodLayout: TextInputLayout
    private lateinit var searchFoodET: TextInputEditText
    private lateinit var barcodeBtn: Button
    private lateinit var addCustomFoodBtn: Button
    private lateinit var foodRV: RecyclerView
    private lateinit var adapter: AddFoodAdapter
    private val barcodeScannerActivityResult = registerForActivityResult(
        BarcodeScannerContract()
    ) { extras ->
        if (extras != null)
            handleResponseFromCamera(extras)
    }
    private lateinit var foodInfoActivityResult: ActivityResultLauncher<String>
    private lateinit var registerFoodActivityResult: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)
        initComponents()
    }

    private fun initComponents() {
        searchFoodLayout = findViewById(R.id.activity_add_food_search_for_food_til)
        searchFoodET = findViewById(R.id.activity_add_food_search_for_food_et)
        barcodeBtn = findViewById(R.id.activity_add_food_barcode_btn)
        barcodeBtn.setOnClickListener(this)
        addCustomFoodBtn = findViewById(R.id.activity_add_food_add_btn)
        addCustomFoodBtn.setOnClickListener(this)
        searchFoodET.addTextChangedListener(setTextWatcher())
        foodRV = findViewById(R.id.activity_add_food_rv)
        setInitialFoods()
    }

    private fun setTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    val searchQuery = s.toString()
                    Log.d("searchQuery", "onTextChanged: $searchQuery")
                    displayFoods(searchQuery)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    val searchQuery = s.toString()
                    displayFoods(searchQuery)
                }
            }
        }
    }

    private fun setInitialFoods(limit: Long = 20) {
        setUpRecyclerView(limit)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.activity_add_food_barcode_btn -> openCameraToScan()
            R.id.activity_add_food_add_btn -> launchRegisterFoodActivity()
        }
    }

    override fun onAdapterItemClick(food: Food) {
        launchFoodInfoActivity(food.id)
    }

    private fun openCameraToScan() {
        barcodeScannerActivityResult.launch(Unit)
    }

    private fun displayFoods(nameToMatch: String) {
        val options = FoodDB.searchForFoodByNamePrefix(nameToMatch)
        adapter.updateOptions(options)
        adapter.notifyDataSetChanged()
    }

    private fun launchRegisterFoodActivity() {
        val intent = Intent(this@AddFoodActivity, RegisterFoodToDbActivity::class.java)
        registerFoodActivityResult.launch("")
    }

    private fun handleResponseFromCamera(extras: Bundle) {
        val foodExists = extras.getBoolean(IntentConstants.EXISTS)
        if (foodExists) {
            val id = extras.getString(Food.ID)!!
            foodInfoActivityResult = registerForActivityResult(
                FoodInfoContract()
            ){

            }

        } else {
            val foodBarcode = extras.getString(Food.BARCODE)!!
            registerFoodActivityResult =
                registerForActivityResult(RegisterFoodContract()) { result ->
                    if (result != null)
                        handleResponseFromRegisterFoodActivity(result)
                }
            registerFoodActivityResult.launch(foodBarcode)
        }
    }


    private fun handleResponseFromRegisterFoodActivity(extras: Bundle) {
        val isAdded = extras.getBoolean(RegisterFoodToDbActivity.IS_FOOD_ADDED, false)
        if (isAdded) {
            setInitialFoods()
        }
    }

    private fun handleResponseFromFoodInfoActivity(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val extras = data!!.extras
        }
    }

    private fun setUpRecyclerView(limit: Long) {
        val options = FoodDB.initialAddFoodAdapterList(limit)
        adapter = AddFoodAdapter(this@AddFoodActivity, this, options)
        foodRV.layoutManager = LinearLayoutManager(this)
        foodRV.itemAnimator = null
        foodRV.adapter = adapter
    }

    private fun launchFoodInfoActivity(foodId: String) {
        foodInfoActivityResult.launch(foodId)
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser == null)
            startActivity(Intent(this@AddFoodActivity, LoginActivity::class.java))
        adapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopListening()
    }

}