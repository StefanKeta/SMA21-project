package com.example.licenta.fragment.main.diary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.licenta.R
import com.google.android.material.tabs.TabLayout

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var addFoodBtn: Button
    private lateinit var remainingProteinTV: TextView
    private lateinit var proteinPB: ProgressBar
    private lateinit var remainingCarbsTV: TextView
    private lateinit var carbsPB: ProgressBar
    private lateinit var remainingFatTV: TextView
    private lateinit var fatPB: ProgressBar
    private lateinit var remainingCaloriesTV: TextView
    private lateinit var caloriesPB: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
    }

    override fun onClick(v: View?) {
        //
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