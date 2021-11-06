package com.example.licenta.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.licenta.R
import com.example.licenta.fragment.main.DiaryFragment
import com.example.licenta.fragment.main.HomeFragment
import com.example.licenta.fragment.main.ProfileFragment
import com.example.licenta.fragment.main.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener, View.OnClickListener{

    private lateinit var navigationBar : BottomNavigationView
    private lateinit var fragmentLayout : FrameLayout
    private lateinit var addFab : FloatingActionButton
    private lateinit var addExerciseFab : FloatingActionButton
    private lateinit var addFoodFab : FloatingActionButton
    private val rotateOpenAnim : Animation by lazy {
        AnimationUtils.loadAnimation(this,R.anim.fab_rotate_open)
    }
    private val rotateCloseAnim : Animation by lazy {
        AnimationUtils.loadAnimation(this,R.anim.fab_rotate_close)
    }
    private val fromBottomAnim : Animation by lazy {
        AnimationUtils.loadAnimation(this,R.anim.fab_from_bottom)
    }
    private val toBottomAnim : Animation by lazy {
        AnimationUtils.loadAnimation(this,R.anim.fab_to_bottom)
    }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
    }

    private fun initComponents(){
        navigationBar = findViewById(R.id.activity_main_navigation_view_bottom)
        fragmentLayout = findViewById(R.id.activity_main_fragment_layout)
        navigationBar.selectedItemId = R.id.menu_main_bottom_home
        switchFragments(HomeFragment())
        navigationBar.setOnItemSelectedListener(this)
        addFab = findViewById(R.id.activity_main_fab_add)
        addFab.setOnClickListener(this)
        addFoodFab = findViewById(R.id.activity_main_fab_food)
        addFoodFab.setOnClickListener(this)
        addExerciseFab = findViewById(R.id.activity_main_fab_exercise)
        addExerciseFab.setOnClickListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_bottom_home -> switchFragments(HomeFragment())
            R.id.menu_main_bottom_search -> switchFragments(SearchFragment())
            R.id.menu_main_bottom_diary ->  switchFragments(DiaryFragment())
            R.id.menu_main_bottom_profile -> switchFragments(ProfileFragment())


        }
        return true
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.activity_main_fab_add -> onAddFabClicked()
        }
    }

    private fun switchFragments(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_main_fragment_layout,fragment)
        fragmentTransaction.commit()
    }

    private fun onAddFabClicked(){
        setButtonsVisibility(clicked)
        setButtonsAnimation(clicked)
        clicked = !clicked
    }

    private fun setButtonsVisibility(clicked:Boolean){
        if (!clicked){
            addExerciseFab.visibility = View.VISIBLE
            addFoodFab.visibility = View.VISIBLE
        }else{
            addExerciseFab.visibility = View.INVISIBLE
            addFoodFab.visibility = View.INVISIBLE
        }
    }

    private fun setButtonsAnimation(clicked: Boolean){
        if (!clicked){
            addExerciseFab.startAnimation(fromBottomAnim)
            addFoodFab.startAnimation(fromBottomAnim)
            addFab.startAnimation(rotateOpenAnim)
        }else{
            addExerciseFab.startAnimation(toBottomAnim)
            addFoodFab.startAnimation(toBottomAnim)
            addFab.startAnimation(rotateCloseAnim)
        }

    }

}