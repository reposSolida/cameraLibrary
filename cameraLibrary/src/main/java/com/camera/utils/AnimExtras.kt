package com.camera.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.camera.presentation.viewModel.DialogFragmentCallback
import com.mylibrary.R

object AnimExtras {

    var pair: Pair<AlertDialog, LottieAnimationView>? = null

    fun toggleAnimDownload(
        show: Boolean,
        msg: String = R.string.downloading_file.toString(),
        context: Context,
        title: String = "Realizando acción...",
        messageTypeIcon: MessageTypeIcon = MessageTypeIcon.INFO
    ) {
        if (show) {
            if (pair != null) dismissData()
            pair = context.customInformationMsgWithAnimation(
                msg,
                R.raw.anim_downloading_document,
                closeable = false,
                title = title,
                messageTypeIcon = messageTypeIcon
            )
        } else {
            dismissData()
        }
    }

    fun toggleAnimNoInternet(
        show: Boolean,
        msg: String = R.string.no_internet_connection.toString(),
        context: Context,
        closeable: Boolean = true,
        textCarousel: Boolean = true,
        title: String = "Sin conexión a internet...",
        messageTypeIcon: MessageTypeIcon = MessageTypeIcon.ERROR
    ) {
        if (show) {
            if (pair != null) dismissData()
            pair = context.customInformationMsgWithAnimation(
                msg,
                R.raw.anim_no_internet_kitty,
                textCarousel = textCarousel,
                closeable = closeable,
                multiLine = true,
            )
            MessageTypeIcon.ERROR.IconType()
        } else {
            dismissData()
        }
    }


    fun toggleAnimLoadingMainData(
        show: Boolean,
        context: Context,
        title: String = "Descargando información...",
        messageTypeIcon: MessageTypeIcon = MessageTypeIcon.INFO
    ) {
        if (show) {
            if (pair != null) dismissData()
            pair = context.customInformationMsgWithAnimation(
                closeable = false,
                textCarousel = true,
                title = title,
                messageTypeIcon = messageTypeIcon
            )

        } else {
            dismissData()
        }
    }


    fun toggleAnimGeneric(
        show: Boolean,
        msg: String = R.string.no_internet_connection.toString(),
        anim: Int = R.raw.anim_download_temp,
        context: Context,
        customSize: Float = -1F,
        closeable: Boolean = false,
        multiline: Boolean = false,
        error: Boolean = false,
        title: String = "Realizando acción, espere por favor...",
        messageTypeIcon: MessageTypeIcon = MessageTypeIcon.INFO,
        loop: Boolean = true,
        dialogFragmentCallback: DialogFragmentCallback? = null
    ) {
        if (pair != null) dismissData()
        if (show) {
            pair = context.customInformationMsgWithAnimation(
                msg,
                anim,
                textCarousel = false,
                closeable = closeable,
                customSize = customSize,
                multiLine = multiline,
                error = error,
                title = title,
                messageTypeIcon = messageTypeIcon,
                loop = loop,
                onDialogFragmentCallback = dialogFragmentCallback
            )
        } else {
            dismissData()
        }
    }

    fun dismissData() {
        pair?.first?.dismiss()
        pair?.second?.cancelAnimation()
    }
}