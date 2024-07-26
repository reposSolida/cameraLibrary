package com.camera.utils

import com.camera.data.models.UserSessionData
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber


fun LogError(msg: String) {
    Timber.e(msg)
    evaluateIfDisplayErrorDialog(msg)
}
fun LogError(msg: String, exception: Exception) {
    Timber.e(exception, "$msg: %s", exception.message ?: "null")
    try {
        // Enviar informe de error a Crashlytics
        FirebaseCrashlytics.getInstance()
            .setUserId(UserSessionData.user?.username.orEmpty().ifEmpty { "null" })
        FirebaseCrashlytics.getInstance().recordException(exception)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    evaluateIfDisplayErrorDialog(exception)
}
fun LogInfo(msg: String) = Timber.d(msg)
fun LogInfo(msg: String, onlyInDebug: Boolean) = if (onlyInDebug) {
    if (GlobalTestValues.debugMode) Timber.d(msg) else ""
} else ""

