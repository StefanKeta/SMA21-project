package com.example.licenta.fragment.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.licenta.R
import com.example.licenta.activity.auth.LoginActivity
import com.example.licenta.data.LoggedUserData
import com.example.licenta.data.LoggedUserGoals
import com.example.licenta.firebase.Auth
import com.example.licenta.fragment.main.profile.ConnectionsFragment
import com.example.licenta.fragment.main.profile.GoalsFragment
import com.example.licenta.fragment.main.profile.PostsFragment
import com.example.licenta.fragment.main.profile.RecordsFragment
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(), TabLayout.OnTabSelectedListener, View.OnClickListener {
    private lateinit var fragmentFrameLayout: FrameLayout
    private lateinit var profilePhoto: ImageView
    private lateinit var nameTV: TextView
    private lateinit var logOut: ImageView
    private lateinit var infoTab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initComponents(view: View) {
        fragmentFrameLayout = view.findViewById(R.id.fragment_profile_items_fragment)
        infoTab = view.findViewById(R.id.fragment_profile_tab_layout)
        infoTab.addOnTabSelectedListener(this)
        profilePhoto = view.findViewById(R.id.fragment_profile_photo_profile_iv)
        logOut = view.findViewById(R.id.fragment_profile_button_log_out_btn)
        nameTV = view.findViewById(R.id.fragment_profile_name_tv)
        nameTV.text =
            "${LoggedUserData.getLoggedUser().firstName} ${LoggedUserData.getLoggedUser().lastName}"
        logOut.setOnClickListener(this)
    }


    private fun switchFragment(fragment: Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_profile_items_fragment, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initComponents(view)
        return view
    }

    override fun onClick(view: View?) {
        if (view!!.id == R.id.fragment_profile_button_log_out_btn) {
            Auth.logUserOut()
            LoggedUserData.setLoggedUser(null)
            LoggedUserGoals.setGoals(null)
            startActivity(Intent(context!!, LoginActivity::class.java))
            activity!!.finish()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab!!.position) {
            0 -> switchFragment(PostsFragment())
            1 -> switchFragment(GoalsFragment())
            2 -> switchFragment(RecordsFragment())
            3 -> switchFragment(ConnectionsFragment())
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        //
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        //
    }
}