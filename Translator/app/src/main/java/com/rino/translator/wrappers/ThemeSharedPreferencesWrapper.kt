package com.rino.translator.wrappers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ThemeSharedPreferencesWrapper(context: Context) {

    companion object {
        private const val THEME_SETTINGS = "THEME_SETTINGS"
        private const val IS_NIGHT_MODE_ENABLED = "IS_NIGHT_MODE_ENABLED"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(THEME_SETTINGS, Context.MODE_PRIVATE)

    var isNightModeEnabled: Boolean
        get() = sharedPreferences.getBoolean(IS_NIGHT_MODE_ENABLED, false)
        set(value) {
            sharedPreferences.edit {
                putBoolean(IS_NIGHT_MODE_ENABLED, value)
            }
        }

}