package com.application.letitsnow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.letitsnow.WeatherViewModel
import com.application.letitsnow.databinding.FragmentStartBinding

class StartFragment : BaseFragment() {

    private var binding: FragmentStartBinding? = null
    private lateinit var viewModel: WeatherViewModel

    companion object {
        const val SELECTED_TOWN = "selectedTown"
        fun newInstance(
            selectedTown: String?,
        ) = StartFragment().apply {
            arguments = Bundle().apply {
                putString(SELECTED_TOWN, selectedTown)
            }
        }
    }

    private var town: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(SELECTED_TOWN)?.let { arg ->
            (arg as? String?)?.let { town = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
     //   viewModel.town.set(town)
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