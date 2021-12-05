package com.example.licenta.fragment.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.licenta.R
import com.example.licenta.fragment.main.diary.ExerciseFragment
import com.example.licenta.fragment.main.diary.FoodFragment
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiaryFragment(val selectedFragment: String = FOOD_FRAGMENT_CODE) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var tabLayout: TabLayout
    private lateinit var fragmentLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)
        initComponents(view)
        return view
    }

    private fun initComponents(view: View) {
        tabLayout = view.findViewById(R.id.fragment_diary_tab_layout)
        fragmentLayout = view.findViewById(R.id.fragment_diary_fragment_frame_layout)
        switchFragment(FoodFragment())
        if (selectedFragment == EXERCISE_FRAGMENT_CODE) {
            tabLayout.getTabAt(1)!!.select()
            switchFragment(ExerciseFragment())
        } else {
            tabLayout.getTabAt(0)!!.select()
            switchFragment(FoodFragment())
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) switchFragment(FoodFragment())
                else switchFragment(ExerciseFragment())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) switchFragment(FoodFragment())
                else switchFragment(ExerciseFragment())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) switchFragment(FoodFragment())
                else switchFragment(ExerciseFragment())
            }

        })
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_diary_fragment_frame_layout, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        const val FOOD_FRAGMENT_CODE: String = "FoodFragment"
        const val EXERCISE_FRAGMENT_CODE: String = "ExerciseFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FoodFragment().apply {
                arguments = Bundle().apply {
                    putString(com.example.licenta.fragment.main.ARG_PARAM1, param1)
                    putString(com.example.licenta.fragment.main.ARG_PARAM2, param2)
                }
            }
    }
}