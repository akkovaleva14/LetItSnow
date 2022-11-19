package com.application.letitsnow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.application.letitsnow.WeatherViewModel
import com.application.letitsnow.data.Weather
import com.application.letitsnow.databinding.FragmentStartBinding
import com.application.letitsnow.network.isOnline

class StartFragment : BaseFragment() {

    private var binding: FragmentStartBinding? = null
    private var viewModel: WeatherViewModel? = null

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
        viewModel = ViewModelProvider(
            this,
            WeatherViewModel.factory((activity as? MainActivity)?.getRepository())
        )[WeatherViewModel::class.java]

        viewModel?.town?.set(town)
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        binding?.buttonSettings?.setOnClickListener {
            val settingsFragment = SettingsFragment()
            replaceFragment(settingsFragment)
        }

        viewModel?.weather?.observe(
            viewLifecycleOwner
        ) { bindWeatherOfTown(it) }

        checkError()
    }

    private fun bindWeatherOfTown(weather: Weather?) {
        binding?.town?.text = weather?.location?.name
        binding?.temperature?.text = weather?.current?.temp_c?.toString()
    }

    private fun checkError() {
        if (isOnline(context)) {
            viewModel?.getCurrentWeather()
            toggle(false)
        } else {
            toggle(true)
        }
    }

    private fun toggle(show: Boolean) {
        val transition: Transition = Fade()
        transition.duration = 600
        transition.addTarget(binding?.toast as View)
        TransitionManager.beginDelayedTransition(
            binding?.root as ViewGroup,
            transition
        )
        binding?.toast?.isVisible = show
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}