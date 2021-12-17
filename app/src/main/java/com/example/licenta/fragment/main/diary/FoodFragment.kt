package com.example.licenta.fragment.main.diary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.activity.diary.AddFoodActivity
import com.example.licenta.adapter.MealsAdapter
import com.example.licenta.model.food.Meal

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodFragment : Fragment(), View.OnClickListener {
    private lateinit var addFoodBtn: Button
    private lateinit var remainingProteinTV: TextView
    private lateinit var proteinPB: ProgressBar
    private lateinit var remainingCarbsTV: TextView
    private lateinit var carbsPB: ProgressBar
    private lateinit var remainingFatTV: TextView
    private lateinit var fatPB: ProgressBar
    private lateinit var remainingCaloriesTV: TextView
    private lateinit var caloriesPB: ProgressBar
    private lateinit var mealsRV : RecyclerView
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var addFoodResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)
        initComponents(view)
        return view
    }

    private fun initComponents(view: View) {
        addFoodBtn = view.findViewById(R.id.fragment_diary_food_add_food_btn)
        addFoodBtn.setOnClickListener(this)
        remainingProteinTV = view.findViewById(R.id.fragment_diary_food_protein_remaining_tv)
        proteinPB = view.findViewById(R.id.fragment_diary_food_progress_bar_protein)
        remainingCarbsTV = view.findViewById(R.id.fragment_diary_food_carbs_remaining_tv)
        carbsPB = view.findViewById(R.id.fragment_diary_food_progress_bar_carbs)
        remainingFatTV = view.findViewById(R.id.fragment_diary_food_fat_remaining_tv)
        fatPB = view.findViewById(R.id.fragment_diary_food_progress_bar_fat)
        remainingCaloriesTV = view.findViewById(R.id.fragment_diary_food_calories_remaining_tv)
        mealsRV = view.findViewById(R.id.fragment_diary_food_rv)
        mealsAdapter = MealsAdapter(requireContext(),ArrayList<Meal>())
        addFoodResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                Log.d("foodExists", "addFoodSucceeded")
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.fragment_diary_food_add_food_btn -> goToAddFoodActivity()
        }
    }

    private fun goToAddFoodActivity(){
        addFoodResultLauncher.launch(Intent(context,AddFoodActivity::class.java))
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