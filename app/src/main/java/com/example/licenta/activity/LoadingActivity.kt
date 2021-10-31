package com.example.licenta.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.licenta.R
import com.example.licenta.activity.auth.LoginActivity
import com.example.licenta.util.InternetConnectionTracker

class LoadingActivity : AppCompatActivity() {
    private lateinit var loadingBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        loadingBar = findViewById(R.id.activity_loading_progress_bar)
        checkConnection()
    }

    private fun checkConnection() : Boolean{
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE)
            as ConnectivityManager
        val networkInfo : Array<NetworkInfo> = connectivityManager.allNetworkInfo
        for (info in networkInfo)
            if(info.state == NetworkInfo.State.CONNECTED) {
                trackInternetConnection()
                return true
            }
        showError()
        return false
    }

    private fun trackInternetConnection(){
        InternetConnectionTracker.trackConnection(this)
        InternetConnectionTracker.observe(this@LoadingActivity,{ isConnected ->
            if(isConnected) goToLogin()
            else showError()
        })
    }

    private fun goToLogin(){
        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity :: class.java))
        },4000)}

    private fun showError(){
        loadingBar.visibility = View.GONE
        Toast.makeText(this,"No internet connection!",Toast.LENGTH_LONG)
            .show()
    }
}
