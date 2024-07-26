package com.camera.data.models.response

import com.camera.data.models.Photo
import com.google.gson.annotations.SerializedName

class PhotosResponse(
    @SerializedName("sdtWSRespuesta") val sdtWSRespuesta: WsRespuesta,
    @SerializedName("sdtColfotos") val photos: List<Photo>
)