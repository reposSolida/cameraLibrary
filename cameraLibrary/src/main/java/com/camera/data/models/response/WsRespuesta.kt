package com.camera.data.models.response

import com.camera.utils.UserLoginState
import com.camera.utils.Utils

data class WsRespuesta(
    var ok: String = UserLoginState.N.toString(),
    var errcod: Long = 2,
    var errdsc: String = "Usuario o Password no validos",
    var empid: Long = 0,
    var dtServidor: String = Utils.getCurrentDateAndTime()
)