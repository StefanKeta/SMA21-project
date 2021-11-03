package com.example.licenta.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.licenta.R
import com.example.licenta.activity.auth.LoginActivity
import com.example.licenta.util.InternetConnectionTracker

class LoadingActivity : AppCompatActivity() {
    private lateinit var loadingBar : ProgressBar
    private var isLaunched = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        loadingBar = findViewById(R.id.activity_loading_progress_bar)
        checkConnection()
    }

    private fun checkConnection() {
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE)
            as ConnectivityManager
        val networkInfo : Array<NetworkInfo> = connectivityManager.allNetworkInfo
        Log.d("networkInfo", "checkConnection: ${networkInfo[0]}")
        for (info in networkInfo)
            if(info.state == NetworkInfo.State.CONNECTED) {
                loadingBar.visibility = View.VISIBLE
                trackInternetConnection()
                return
            }
        showError()
    }

    private fun trackInternetConnection(){
        InternetConnectionTracker.trackConnection(this)
        InternetConnectionTracker.observe(this@LoadingActivity,{ isConnected ->
            if(isConnected) {
                if(!isLaunched) {
                    goToLogin()
                    isLaunched = true
                } else
                    goToLogin(1000)
            }
            else showError()
        })
    }

    private fun goToLogin(duration:Long = 3000){
        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity :: class.java))
        },duration)
    }

    private fun showError(){
        loadingBar.visibility = View.GONE
        Toast.makeText(this,"No internet connection!",Toast.LENGTH_LONG)
            .show()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}
