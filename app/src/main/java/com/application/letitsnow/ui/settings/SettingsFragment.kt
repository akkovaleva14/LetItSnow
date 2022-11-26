package com.application.letitsnow.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.application.letitsnow.R
import com.application.letitsnow.ui.start.StartViewModel
import com.application.letitsnow.databinding.FragmentSettingsBinding
import com.application.letitsnow.utils.isOnline
import com.application.letitsnow.ui.BaseFragment
import com.application.letitsnow.ui.MainActivity
import com.application.letitsnow.ui.start.StartFragment

class SettingsFragment(val listener: OnSelectedTownClickListener) : BaseFragment(),
    AdapterView.OnItemSelectedListener {

    private var binding: FragmentSettingsBinding? = null
    private var viewModel: StartViewModel? = null
    private var town: String? = null

    companion object {
        fun newInstance(listener: OnSelectedTownClickListener) = SettingsFragment(listener)

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
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        binding?.buttonArrowBack?.setOnClickListener {
            checkError()
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
            checkError()
        }
        binding?.spinner?.onItemSelectedListener = this

        checkError()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using parent.getItemAtPosition(pos)
        val selectedTown: String = parent.getItemAtPosition(pos).toString()
        listener.onTownClick(selectedTown)

        checkError()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        StartFragment.newInstance("Saint-Petersburg")
        checkError()
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