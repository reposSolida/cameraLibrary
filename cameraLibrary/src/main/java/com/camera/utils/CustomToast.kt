package com.camera.utils

import android.widget.Toast

/**
 ** Created by Carlos A. Novaez Guerrero on 3/8/2023 2:31 PM
 ** cnovaez.dev@outlook.com
 **/
data class CustomToast (var bodyToast: String,
                        var titleToast: String = MessageTypeIcon.INFO.toString(),
                        var duration: Int = Toast.LENGTH_SHORT,
                        var picId: Int = MessageTypeIcon.INFO.IconType(),
                        var cardColor: Int = -1)