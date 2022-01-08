package com.example.licenta.fragment.main.diary

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.adapter.FoodAdapter
import com.example.licenta.contract.AddedFoodForUserContract
import com.example.licenta.data.LoggedUserData
import com.example.licenta.data.LoggedUserGoals
import com.example.licenta.firebase.db.FoodDB
import com.example.licenta.firebase.db.SelectedFoodDB
import com.example.licenta.fragment.main.OnDateChangedListener
import com.example.licenta.model.food.FoodMeasureUnitEnum
import com.example.licenta.model.food.SelectedFood
import com.example.licenta.util.Date
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodFragment(private var date: String = Date.setCurrentDay()) : Fragment(),
    View.OnClickListener, OnDateChangedListener, FoodAdapter.OnSelectedFoodLongClickListener,
    FoodAdapter.OnSelectedFoodClickListener {
    private lateinit var addFoodBtn: Button
    private lateinit var remainingProteinTV: TextView
    private lateinit var proteinPB: LinearProgressIndicator
    private lateinit var remainingCarbsTV: TextView
    private lateinit var carbsPB: LinearProgressIndicator
    private lateinit var remainingFatTV: TextView
    private lateinit var fatPB: LinearProgressIndicator
    private lateinit var remainingCaloriesTV: TextView
    private lateinit var caloriesPB: CircularProgressIndicator
    private lateinit var foodRV: RecyclerView
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var addFoodForUserContract: ActivityResultLauncher<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_food, container, false)
        initComponents(view)
        return view
    }

    private fun initComponents(view: View) {
        addFoodBtn = view.findViewById(R.id.fragment_diary_food_add_food_btn)
        addFoodBtn.setOnClickListener(this)
        setUpProgressBars(view)
        setUpRecyclerView(view)
        addFoodForUserContract = registerForActivityResult(AddedFoodForUserContract()) { isSaved ->
            if (isSaved)
                foodAdapter.notifyDataSetChanged()
            else {
                Toast.makeText(context, "Could not add to foods!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fragment_diary_food_add_food_btn -> goToAddFoodActivity()
        }
    }

    override fun onSelectedFoodClick(id: String) {
        val view = LayoutInflater
            .from(context!!)
            .inflate(R.layout.dialog_edit_selected_food, null, false)
        val quantityTIL: TextInputLayout =
            view.findViewById(R.id.dialog_edit_selected_food_quantity_til)
        val quantityET: TextInputEditText =
            view.findViewById(R.id.dialog_edit_selected_food_quantity_et)
        val unitGroup: MaterialButtonToggleGroup =
            view.findViewById(R.id.dialog_edit_selected_food_quantity_tbg)
        val gBtn: Button = view.findViewById(R.id.dialog_edit_selected_food_g_rb)

        AlertDialog
            .Builder(context!!)
            .setView(view)
            .setTitle("Edit selected food")
            .setIcon(R.drawable.ic_baseline_edit_24)
            .setPositiveButton("Edit") { dialog, _ ->
                if (quantityET.text.isNullOrEmpty()) {
                    quantityTIL.error = "Please set the quantity"
                } else {
                    val quantity = quantityET.text.toString().trim().toDouble() / 100.0
                    val unit =
                        if (unitGroup.checkedButtonId == gBtn.id) FoodMeasureUnitEnum.GRAM else FoodMeasureUnitEnum.OZ
                    SelectedFoodDB
                        .updateSelectedFood(id, quantity, unit) { isEdited ->
                            if (isEdited) {
                                refreshFood()
                                dialog.dismiss()
                            } else {
                                dialog.dismiss()
                            }
                        }
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onSelectedFoodLongClick(id: String): Boolean {
        AlertDialog
            .Builder(context)
            .setTitle("Are you sure you want to delete selected food?")
            .setIcon(R.drawable.ic_baseline_delete_24)
            .setPositiveButton("Yes") { dialog, _ ->
                SelectedFoodDB.removeSelectedFood(id) { isRemoved ->
                    if (isRemoved) {
                        dialog.dismiss()
                        refreshFood()
                    } else {
                        dialog.dismiss()
                        Toast.makeText(context!!, "Could not remove food!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
        return true
    }


    private fun setUpProgressBars(view: View) {
        remainingProteinTV = view.findViewById(R.id.fragment_diary_food_protein_remaining_tv)
        proteinPB = view.findViewById(R.id.fragment_diary_food_progress_bar_protein)
        proteinPB.max = LoggedUserGoals.getGoals().protein
        remainingCarbsTV = view.findViewById(R.id.fragment_diary_food_carbs_remaining_tv)
        carbsPB = view.findViewById(R.id.fragment_diary_food_progress_bar_carbs)
        carbsPB.max = LoggedUserGoals.getGoals().carbs
        remainingFatTV = view.findViewById(R.id.fragment_diary_food_fat_remaining_tv)
        fatPB = view.findViewById(R.id.fragment_diary_food_progress_bar_fat)
        fatPB.max = LoggedUserGoals.getGoals().fat
        remainingCaloriesTV = view.findViewById(R.id.fragment_diary_food_calories_remaining_tv)
        caloriesPB = view.findViewById(R.id.fragment_diary_food_calories_remaining_pb)
        caloriesPB.max = LoggedUserGoals.getGoals().calories
        SelectedFoodDB.getSelectedFoodByDateAndId(
            LoggedUserData.getLoggedUser().uuid,
            date,
            ::updateMacrosAndCalories
        )
    }

    private fun setUpRecyclerView(view: View) {
        foodRV = view.findViewById(R.id.fragment_diary_food_rv)
        foodRV.layoutManager = LinearLayoutManager(context)
        foodRV.hasFixedSize()
        foodRV.itemAnimator = null
        foodAdapter =
            FoodAdapter(context!!, SelectedFoodDB.getSelectedFoodsOption(date), this, this)
        foodRV.adapter = foodAdapter
    }

    private fun updateMacrosAndCalories(selectedFoods: List<SelectedFood>) {
        proteinPB.progress = 0
        carbsPB.progress = 0
        fatPB.progress = 0
        caloriesPB.progress = 0
        remainingProteinTV.text = proteinPB.max.toString()
        remainingCarbsTV.text = carbsPB.max.toString()
        remainingFatTV.text = fatPB.max.toString()
        remainingCaloriesTV.text = caloriesPB.max.toString()
        var proteinProgress = 0
        var carbsProgress = 0
        var fatProgress = 0
        var calorieProgress = 0
        selectedFoods.map { selectedFood ->
            FoodDB.getFoodById(selectedFood.foodId) { food ->
                val quantity = selectedFood.quantity
                proteinProgress += (food.protein * quantity).toInt()
                carbsProgress += (food.carbs * quantity).toInt()
                fatProgress += (food.fat * quantity).toInt()
                calorieProgress += (food.calories * quantity).toInt()
                proteinPB.progress = proteinProgress
                carbsPB.progress = carbsProgress
                fatPB.progress = fatProgress
                caloriesPB.progress = calorieProgress
                remainingProteinTV.text = (proteinPB.max - proteinProgress).toString()
                remainingCarbsTV.text = (carbsPB.max - carbsProgress).toString()
                remainingFatTV.text = (fatPB.max - fatProgress).toString()
                remainingCaloriesTV.text = (caloriesPB.max - calorieProgress).toString()
            }
        }
    }

    private fun goToAddFoodActivity() {
        addFoodForUserContract.launch(date)
    }

    override fun onStart() {
        super.onStart()
        foodAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        foodAdapter.stopListening()
    }

    override fun changeDate(date: String) {
        this.date = date
        foodAdapter.updateOptions(SelectedFoodDB.getSelectedFoodsOption(date))
        SelectedFoodDB.getSelectedFoodByDateAndId(
            LoggedUserData.getLoggedUser().uuid,
            date,
            ::updateMacrosAndCalories
        )
        foodAdapter.notifyDataSetChanged()
    }

    private fun refreshFood() {
        SelectedFoodDB.getSelectedFoodByDateAndId(
            LoggedUserData.getLoggedUser().uuid,
            date,
            ::updateMacrosAndCalories
        )
        foodAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FoodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FoodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}