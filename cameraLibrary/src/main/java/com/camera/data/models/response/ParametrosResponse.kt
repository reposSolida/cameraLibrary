package com.camera.data.models.response

import com.camera.data.models.Parametro
import com.google.gson.annotations.SerializedName

class ParametrosResponse(
    @SerializedName("sdtWSRespuesta") val sdtWSRespuesta: WsRespuesta,
    @SerializedName("sdtColParametros") val parametros: List<Parametro>
)