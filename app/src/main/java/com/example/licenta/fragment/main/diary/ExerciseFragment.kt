package com.example.licenta.fragment.main.diary

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.licenta.R
import com.example.licenta.activity.diary.AddExerciseActivity
import com.example.licenta.adapter.ExerciseAdapter
import com.example.licenta.contract.AddExerciseContract
import com.example.licenta.firebase.db.WeightExerciseRecordDB
import com.example.licenta.fragment.main.OnDateChangedListener
import com.example.licenta.util.Date
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment(private var date: String = Date.setCurrentDay()) : Fragment(),
    View.OnClickListener, OnDateChangedListener {
    private lateinit var addExerciseBtn: Button
    private lateinit var exercisesRV: RecyclerView
    private lateinit var adapter : ExerciseAdapter
    private lateinit var addExerciseContract: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)
        initComponents(view)
        return view
    }

    private fun initComponents(view: View) {
        addExerciseBtn = view.findViewById(R.id.fragment_diary_exercise_add_btn)
        addExerciseBtn.setOnClickListener(this)
        exercisesRV = view.findViewById(R.id.fragment_diary_exercise_rv)
        exercisesRV.layoutManager = LinearLayoutManager(context!!)
        exercisesRV.hasFixedSize()
        exercisesRV.itemAnimator = null
        setUpAdapter()
        addExerciseContract = registerForActivityResult(
            AddExerciseContract(),
            ::handleResponseFromAddExerciseActivity
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fragment_diary_exercise_add_btn -> addExerciseContract.launch(date)
        }
    }

    override fun changeDate(date: String) {
        this.date = date
        refreshAdapter()
    }

    private fun handleResponseFromAddExerciseActivity(isAdded: Boolean) {
        if(isAdded)
            refreshAdapter()
    }

    private fun refreshAdapter(){
        val options = WeightExerciseRecordDB.exerciseAdapterOptions(date)
        adapter.updateOptions(options)
        adapter.notifyDataSetChanged()
    }

    private fun setUpAdapter(){
        val options = WeightExerciseRecordDB.exerciseAdapterOptions(date)
        adapter = ExerciseAdapter(context!!,options)
        exercisesRV.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopListening()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}