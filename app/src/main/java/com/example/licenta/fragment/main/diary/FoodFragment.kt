package com.example.licenta.fragment.main.diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.adapter.FoodAdapter
import com.example.licenta.contract.AddedFoodForUserContract
import com.example.licenta.firebase.db.SelectedFoodDB
import com.example.licenta.fragment.main.OnDateChangedListener
import com.example.licenta.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodFragment(private var date:String = Date.setCurrentDay()) : Fragment(), View.OnClickListener,OnDateChangedListener {
    private lateinit var addFoodBtn: Button
    private lateinit var remainingProteinTV: TextView
    private lateinit var proteinPB: ProgressBar
    private lateinit var remainingCarbsTV: TextView
    private lateinit var carbsPB: ProgressBar
    private lateinit var remainingFatTV: TextView
    private lateinit var fatPB: ProgressBar
    private lateinit var remainingCaloriesTV: TextView
    private lateinit var caloriesPB: ProgressBar
    private lateinit var foodRV : RecyclerView
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
        remainingProteinTV = view.findViewById(R.id.fragment_diary_food_protein_remaining_tv)
        proteinPB = view.findViewById(R.id.fragment_diary_food_progress_bar_protein)
        remainingCarbsTV = view.findViewById(R.id.fragment_diary_food_carbs_remaining_tv)
        carbsPB = view.findViewById(R.id.fragment_diary_food_progress_bar_carbs)
        remainingFatTV = view.findViewById(R.id.fragment_diary_food_fat_remaining_tv)
        fatPB = view.findViewById(R.id.fragment_diary_food_progress_bar_fat)
        remainingCaloriesTV = view.findViewById(R.id.fragment_diary_food_calories_remaining_tv)
        setUpRecyclerView(view)
        addFoodForUserContract = registerForActivityResult(AddedFoodForUserContract()){ isSaved ->
            if(isSaved)
                foodAdapter.notifyDataSetChanged()
            else{
                Toast.makeText(context, "Could not add to foods!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.fragment_diary_food_add_food_btn -> goToAddFoodActivity()
        }
    }

    private fun goToAddFoodActivity(){
        addFoodForUserContract.launch(date)
    }

    private fun setUpRecyclerView(view: View){
        foodRV = view.findViewById(R.id.fragment_diary_food_rv)
        foodRV.layoutManager = LinearLayoutManager(context)
        foodRV.hasFixedSize()
        foodRV.itemAnimator = null
        foodAdapter = FoodAdapter(context!!,SelectedFoodDB.getSelectedFoodsOption(date))
        foodRV.adapter = foodAdapter
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