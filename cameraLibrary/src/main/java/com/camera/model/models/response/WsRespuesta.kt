package com.camera.model.models.response

import com.camera.model.utils.UserLoginState
import com.camera.model.utils.Utils

data class WsRespuesta(
    var ok: String = UserLoginState.N.toString(),
    var errcod: Long = 2,
    var errdsc: String = "Usuario o Password no validos",
    var empid: Long = 0,
    var dtServidor: String = Utils.getCurrentDateAndTime()
)