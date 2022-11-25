package com.application.letitsnow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.application.letitsnow.R
import com.application.letitsnow.WeatherRepository
import com.application.letitsnow.network.ApiService
import com.application.letitsnow.network.RetrofitClient
import com.application.letitsnow.ui.start.StartFragment

class MainActivity : AppCompatActivity() {
    var apiService: ApiService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiService = RetrofitClient.getInstance()

        setContentView(R.layout.activity_main)
    }


    fun getRepository(): WeatherRepository? {
        return apiService?.let { WeatherRepository(it) }
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