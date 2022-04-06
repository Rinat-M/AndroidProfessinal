package com.rino.translator.ui.main

import androidx.lifecycle.ViewModel
import com.rino.translator.wrappers.ThemeSharedPreferencesWrapper

class MainViewModel(
    private val themeSharedPreferencesWrapper: ThemeSharedPreferencesWrapper,
) : ViewModel() {

    val isNightModeEnabled get() = themeSharedPreferencesWrapper.isNightModeEnabled

}