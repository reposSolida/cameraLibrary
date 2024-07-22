package com.camera.data.models.response

import com.google.gson.annotations.SerializedName

class CategoriasResponse(
    @SerializedName("sdtWSRespuesta") val sdtWSRespuesta: WsRespuesta,
    @SerializedName("sdtColCategorias") val categorias: List<com.camera.data.models.Categoria>
)