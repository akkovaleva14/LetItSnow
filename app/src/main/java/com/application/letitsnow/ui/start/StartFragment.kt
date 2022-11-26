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
import com.application.letitsnow.ui.settings.OnSelectedTownClickListener
import com.application.letitsnow.ui.settings.SettingsFragment

class StartFragment : BaseFragment() {

    private var binding: FragmentStartBinding? = null
    private var viewModel: StartViewModel? = null
    private val callback = object : OnSelectedTownClickListener{
        override fun onTownClick(town: String) {
            viewModel?.onTownChanged(town)
        }
    }

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            StartViewModel.factory((activity as? MainActivity)?.getRepository())
        )[StartViewModel::class.java]

        binding = FragmentStartBinding.inflate(inflater, container, false)

        binding?.apply {
            data = viewModel
            lifecycleOwner = this@StartFragment
        }
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

        arguments?.getString(SELECTED_TOWN)?.let { arg ->
            (arg as? String)?.let {
                viewModel?.town?.set(it)
            }
        }

        binding?.buttonSettings?.setOnClickListener {
            replaceFragment(SettingsFragment.newInstance(callback))
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