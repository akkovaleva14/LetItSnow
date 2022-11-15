package com.application.letitsnow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.letitsnow.R
import com.application.letitsnow.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment() {

    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        binding?.buttonArrowBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}