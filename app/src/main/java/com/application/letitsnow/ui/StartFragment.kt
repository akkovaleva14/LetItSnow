package com.application.letitsnow.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.letitsnow.R
import com.application.letitsnow.databinding.FragmentStartBinding

class StartFragment : BaseFragment() {

    private var binding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "sasha")
        binding?.buttonSettings?.setOnClickListener {
            Log.d(TAG, "sasha!!")
            val settingsFragment = SettingsFragment()
            replaceFragment(settingsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}