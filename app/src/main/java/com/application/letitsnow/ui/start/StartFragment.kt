package com.application.letitsnow.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.application.letitsnow.WeatherSharedPreferences
import com.application.letitsnow.data.Weather
import com.application.letitsnow.databinding.FragmentStartBinding
import com.application.letitsnow.ui.BaseFragment
import com.application.letitsnow.ui.MainActivity
import com.application.letitsnow.ui.settings.OnSelectedTownClickListener
import com.application.letitsnow.ui.settings.SettingsFragment
import com.application.letitsnow.utils.isOnline

class StartFragment : BaseFragment() {

    private var binding: FragmentStartBinding? = null
    private var viewModel: StartViewModel? = null
    private var sharedPreferences: WeatherSharedPreferences? = null
    private val callback = object : OnSelectedTownClickListener {
        override fun onTownClick(town: String) {
            viewModel?.onTownChanged(town)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            StartViewModel.factory((activity as? MainActivity)?.getRepository(), context)
        )[StartViewModel::class.java]

        binding = FragmentStartBinding.inflate(inflater, container, false)
        sharedPreferences = context?.let { WeatherSharedPreferences(it) }

        if (!isOnline(context)) {
            viewModel?.town?.set(sharedPreferences?.getTown())
            viewModel?.temp?.set(sharedPreferences?.getTemperature())
        } else {
            viewModel?.town?.set(
                sharedPreferences?.getTown() ?: "Saint Petersburg"
            )
            viewModel?.getCurrentWeather()
        }

        binding?.apply {
            data = viewModel
            lifecycleOwner = this@StartFragment
        }
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

        binding?.buttonSettings?.setOnClickListener {
            replaceFragment(SettingsFragment.newInstance(callback))
        }

        viewModel?.weather?.observe(
            viewLifecycleOwner
        ) { bindWeatherOfTown(it) }

    }

    private fun bindWeatherOfTown(weather: Weather?) {
        binding?.town?.text = weather?.location?.name
        binding?.temperature?.text = weather?.current?.temp_c?.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}