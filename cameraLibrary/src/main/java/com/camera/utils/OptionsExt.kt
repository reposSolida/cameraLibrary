package com.camera.utils

import android.content.Context
import com.camera.data.models.UserProfile

fun Context.getOptionsSharedPreferences() = getSharedPreferences("Options", Context.MODE_PRIVATE)
fun getOptionState(context: Context, value: String): Boolean {
    return context.getOptionsSharedPreferences().getBoolean(
        value,
        (OptionValues.reminderSyncData == value || (OptionValues.autoFocusSearchView == value))
    )
}

