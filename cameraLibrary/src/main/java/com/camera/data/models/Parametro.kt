package com.camera.data.models

import com.camera.data.models.entities.ParametrosEntity
import com.google.gson.annotations.SerializedName
data class Parametro(
    @SerializedName("EmpId")
    val empID: Long,

    @SerializedName("Parid")
    val parid: String,

    @SerializedName("ParDsc")
    val parDsc: String,

    @SerializedName("ParNum")
    val parNum: Long,

    @SerializedName("ParDate")
    val parDate: String,

    @SerializedName("ParDateTime")
    val parDateTime: String,

    @SerializedName("ParTxt")
    val parTxt: String,

    @SerializedName("ParTipo")
    val parTipo: String,

    @SerializedName("ParModDT")
    val parModDT: String,

    @SerializedName("GrupoParmId")
    val grupoParmID: String
) {
    fun toEntity() = ParametrosEntity(
        empID = empID,
        parid = parid,
        parDsc = parDsc,
        parNum = parNum,
        parDate = parDate,
        parDateTime = parDateTime,
        parTxt = parTxt,
        parTipo = parTipo,
        parModDT = parModDT,
        grupoParmID = grupoParmID
    )

}


