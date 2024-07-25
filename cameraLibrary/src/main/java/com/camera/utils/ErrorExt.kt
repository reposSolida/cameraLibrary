package com.camera.utils

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.mylibrary.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.UnknownHostException
@ApplicationContext
private lateinit var context: Context
fun evaluateIfDisplayErrorDialog(ex: Exception) {
    CoroutineScope(Dispatchers.Main).launch {
        if (AnimExtras.pair != null && AnimExtras.pair?.first != null && AnimExtras.pair?.first!!.isShowing) {
            AnimExtras.dismissData()
            if (ex is SocketException || ex is UnknownHostException) {
                AnimExtras.toggleAnimGeneric(
                    true,
                    getString(context, R.string.connectivity_error_msg_label),
                    anim = R.raw.anim_no_internet_kitty,
                    closeable = true,
                    multiline = true,
                    context = context
                )
                if (getGeneralSP(context).syncState) {
                    // TODO implementar manera de que se llame a un objeto ejecute esto desde la activity
                    /*
                    fragmentActivity?.navigate(R.id.loginFragment)
                    storeModDTState(context, true)
                    closeSession(context)

                     */
                }
            } else {
                AnimExtras.toggleAnimGeneric(
                    true,
                    getString(context, R.string.error_occurred_try_again_label),
                    anim = R.raw.anim_error_temp,
                    closeable = true,
                    multiline = true,
                    error = true,
                    context = context
                )
            }
        }
    }
}

fun evaluateIfDisplayErrorDialog(msg: String) {
    CoroutineScope(Dispatchers.Main).launch {
        if (msg.contains("Error al obtener variables POST")) {
            LogError(getString(context, R.string.device_and_server_differ_in_more_than_one_hour_label))
            AnimExtras.toggleAnimGeneric(
                true,
                getString(context, R.string.device_and_server_differ_in_more_than_one_hour_label),
                anim = R.raw.anim_error_temp,
                closeable = true,
                multiline = true,
                error = true,
                // TODO implementar manera de que se llame a un objeto que lance el dialog fragment desde la activity
                /*dialogFragmentCallback = object : DialogFragmentCallback {
                    override fun onFinishEditDialog(obj: Any?, pos: Int) {
                        if (getGeneralSP(context).syncState) {
                            Utils.currActivity()?.navigate(R.id.loginFragment)
                            storeModDTState(context, true)
                            closeSession(context)
                        }
                    }
                },*/
                context = context
            )
        }
    }
}
