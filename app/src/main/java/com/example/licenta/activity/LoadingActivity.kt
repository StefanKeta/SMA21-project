package com.example.licenta.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.licenta.R
import com.example.licenta.activity.auth.LoginActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val handler = Handler()
            .postDelayed({
                startActivity(Intent(this@LoadingActivity,LoginActivity::class.java))
            },3000)
    }
}
