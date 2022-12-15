package com.application.letitsnow.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.application.letitsnow.App
import com.application.letitsnow.R
import com.application.letitsnow.WeatherRepository
import com.application.letitsnow.network.RetrofitClient
import com.application.letitsnow.ui.start.StartFragment

class MainActivity : AppCompatActivity() {
    var apiService = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*if (App.getApp()?.isNightModeEnabled() == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/

      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) //For night mode theme
     //   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //For day mode theme
        setContentView(R.layout.activity_main)
    }

    fun getRepository(): WeatherRepository? {
        return WeatherRepository(apiService)
    }

    override fun onResume() {
        super.onResume()
        openFragment(StartFragment())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.activity_main, fragment)
        transaction.commit()
    }
}