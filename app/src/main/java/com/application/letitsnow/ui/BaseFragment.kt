package com.application.letitsnow.ui

import androidx.fragment.app.Fragment
import com.application.letitsnow.R
import com.application.letitsnow.ui.settings.SettingsFragment

abstract class BaseFragment : Fragment() {
    fun replaceFragment(fragment: BaseFragment, fragmentTag: String? = null) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.activity_main, fragment, fragmentTag)
            ?.addToBackStack(fragmentTag)
            ?.commit()
    }
}