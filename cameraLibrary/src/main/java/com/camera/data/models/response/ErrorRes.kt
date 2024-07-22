package com.camera.data.models.response

import com.google.gson.annotations.SerializedName

data class ErrorRes(
    @SerializedName("ErrorCod")
    val errorCod: Long,

    @SerializedName("ErrorDsc")
    val errorDsc: String,

    @SerializedName("ErrorLinea")
    val errorLinea: Long
)