package com.camera.utils

import android.content.Context

fun Context.getGeneralSharedPreferences() = getSharedPreferences("General", Context.MODE_PRIVATE)
fun getGeneralSP(context: Context): GeneralPreferences {
    return GeneralPreferences(
        modDT = context.getGeneralSharedPreferences().getBoolean("modDT", false),
        syncState = context.getGeneralSharedPreferences().getBoolean("syncState", false),
    )
}

fun storeModDTState(context: Context, modDT: Boolean) {
    context.getGeneralSharedPreferences().edit()
        .putBoolean("modDT", modDT)
        .apply()
}

fun storeSyncState(context: Context, syncState: Boolean) {
    context.getGeneralSharedPreferences().edit()
        .putBoolean("syncState", syncState)
        .apply()
}