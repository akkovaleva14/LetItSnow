package com.application.letitsnow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.letitsnow.databinding.FragmentStartBinding

class StartFragment : BaseFragment() {

    private var binding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        binding?.buttonSettings?.setOnClickListener {
            val settingsFragment = SettingsFragment()
            replaceFragment(settingsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}