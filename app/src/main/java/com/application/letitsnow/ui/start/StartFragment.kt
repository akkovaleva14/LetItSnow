package com.application.letitsnow.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.application.letitsnow.data.Weather
import com.application.letitsnow.databinding.FragmentStartBinding
import com.application.letitsnow.ui.BaseFragment
import com.application.letitsnow.ui.MainActivity
import com.application.letitsnow.ui.settings.SettingsFragment

class StartFragment : BaseFragment() {

    private var binding: FragmentStartBinding? = null
    private var viewModel: StartViewModel? = null

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
            StartViewModel.factory((activity as? MainActivity)?.getRepository())
        )[StartViewModel::class.java]

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

     //   checkError()
    }

    private fun bindWeatherOfTown(weather: Weather?) {
        binding?.town?.text = weather?.location?.name
        binding?.temperature?.text = weather?.current?.temp_c?.toString()
    }

    /*private fun checkError() {
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
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}