package com.example.licenta.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.licenta.R
import com.example.licenta.util.Util

class LoginActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: Button
    private lateinit var rootLayout : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initComponents()
    }

    private fun initComponents(){
        supportActionBar?.hide()
        rootLayout = findViewById(R.id.activity_login_parent_linear_layout)
        rootLayout.setOnClickListener(this)
        emailET = findViewById(R.id.activity_login_email_et)
        passwordET = findViewById(R.id.activity_login_password_et)
        loginBtn = findViewById(R.id.activity_login_login_btn)
        loginBtn.setOnClickListener(this)
        signUpBtn = findViewById(R.id.activity_login_sign_up_btn)
        signUpBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.activity_login_login_btn -> Toast.makeText(this@LoginActivity,"Log in clicked", Toast.LENGTH_SHORT)
                .show()
            R.id.activity_login_sign_up_btn -> startActivity(Intent(this@LoginActivity,
                RegisterActivity::class.java))
            R.id.activity_login_parent_linear_layout -> Util.hideKeyboard(this@LoginActivity)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }


}