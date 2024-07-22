package com.camera.data.models.response

import com.camera.data.models.Archivo
import com.google.gson.annotations.SerializedName

class ArchivosResponse(
    @SerializedName("sdtWSRespuesta") val sdtWSRespuesta: WsRespuesta,
    @SerializedName("sdtColarchivos")  val archivos: List<Archivo>
)