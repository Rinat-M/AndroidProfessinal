package com.rino.translator.ui

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(@StringRes stringId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringId, duration).show()
}

fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}