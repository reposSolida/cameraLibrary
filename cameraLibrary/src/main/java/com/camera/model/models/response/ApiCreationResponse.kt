package com.camera.model.models.response

import com.camera.model.models.documentos.Documento
import com.google.gson.annotations.SerializedName

class ApiCreationResponse(
    @SerializedName("sdtWSRespuesta") val sdtWSRespuesta: WsRespuesta,
    @SerializedName("cantRegProcesados") val quantity: Long,
    @SerializedName("cantRegConError") val quantityWithError: Long,
    @SerializedName("sdtColErrores") val errors: List<ErrorRes>,
    @SerializedName("jsonResponse") val jsonResponse: String,
    @SerializedName("SdtDocumentosItem") val responseDoc: Documento?
)