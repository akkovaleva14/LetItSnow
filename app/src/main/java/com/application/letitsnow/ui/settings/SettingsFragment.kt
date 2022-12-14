package com.application.letitsnow.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.application.letitsnow.App
import com.application.letitsnow.R
import com.application.letitsnow.WeatherSharedPreferences
import com.application.letitsnow.databinding.FragmentSettingsBinding
import com.application.letitsnow.ui.BaseFragment
import com.application.letitsnow.ui.MainActivity
import com.application.letitsnow.ui.start.StartViewModel

class SettingsFragment(private val listener: OnSelectedTownClickListener) : BaseFragment(),
    AdapterView.OnItemSelectedListener {

    private var binding: FragmentSettingsBinding? = null
    private var viewModel: StartViewModel? = null

    companion object {
        fun newInstance(listener: OnSelectedTownClickListener) = SettingsFragment(listener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            StartViewModel.factory((activity as? MainActivity)?.getRepository(), context)
        )[StartViewModel::class.java]

        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        binding?.buttonArrowBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding?.spinner?.adapter = adapter

            binding?.spinner?.setSelection(adapter.getPosition(context?.let {
                WeatherSharedPreferences(it).getTown()
            }))
        }
        binding?.spinner?.onItemSelectedListener = this


        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            binding?.switchIt?.isChecked = true

        binding?.switchIt?.setOnCheckedChangeListener { buttonView, isChecked ->
            App.switchThemeMode(isChecked)

            context?.let { WeatherSharedPreferences(it).setThemeMode(isChecked) }

            replaceFragment(SettingsFragment(listener))
        }
    }

    private fun View.setColor(res: Int) {
        context?.let { ctx ->
            ContextCompat.getColor(ctx, res)
        }?.let {
            this.setBackgroundColor(it)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using parent.getItemAtPosition(pos)
        val selectedTown: String = parent.getItemAtPosition(pos).toString()
        listener.onTownClick(selectedTown)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}