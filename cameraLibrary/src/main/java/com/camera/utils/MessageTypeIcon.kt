package com.camera.utils

import com.mylibrary.R

enum class MessageTypeIcon {
    INFO, WARNING, ERROR, NOT_ALLOWED;

    fun IconType(): Int {
        return when (this) {
            INFO -> R.drawable.info
            WARNING -> R.drawable.warning
            ERROR -> R.drawable.error
            NOT_ALLOWED -> R.drawable.error
        }
    }

    fun SvgType(): Int {
        return when (this) {
            INFO -> R.drawable.ic_info_light_blue
            WARNING -> R.drawable.ic_warning_light_yellow
            ERROR -> R.drawable.ic_error_light_red
            NOT_ALLOWED -> R.drawable.ic_error_light_red
        }
    }

    override fun toString(): String {
        return when (this) {
            INFO -> "Información"
            WARNING -> "Advertencia"
            ERROR -> "Error"
            NOT_ALLOWED -> "No Permitido"
        }
    }
}