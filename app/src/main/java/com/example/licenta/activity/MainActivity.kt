package com.example.licenta.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.licenta.R
import com.example.licenta.fragment.main.DiaryFragment
import com.example.licenta.fragment.main.HomeFragment
import com.example.licenta.fragment.main.ProfileFragment
import com.example.licenta.fragment.main.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener{

    private lateinit var navigationBar : BottomNavigationView
    private lateinit var fragmentLayout : FrameLayout

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

    private fun switchFragments(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_main_fragment_layout,fragment)
        fragmentTransaction.commit()
    }


}