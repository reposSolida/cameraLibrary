package com.camera.data.models.documentos

import com.google.gson.annotations.SerializedName

data class DocumentosLineasDtos(
    @SerializedName("EmpId")
    var empID: Long,

    @SerializedName("TpoDocId")
    val tpoDocID: String,

    @SerializedName("DocUsuId")
    val docUsuID: String,

    @SerializedName("DocId")
    var docID: String,

    @SerializedName("DocLinId")
    val docLinID: Int,

    @SerializedName("DocLinPolId")
    val docLinPolID: String,

    @SerializedName("DocLinDtoOrd")
    val docLinDtoOrd: Long,

    @SerializedName("DocLinDtoPrc")
    val docLinDtoPrc: Double,

    @SerializedName("DocLinDtoImp")
    val docLinDtoImp: Double,

    @SerializedName("DocLinModAp")
    val docLinModAp: String,

    @SerializedName("PolId")
    val polID: String,

    @SerializedName("EscVig")
    val escVig: String,

    @SerializedName("DocLinDtoModDT")
    val modDT: String?,

    @SerializedName("DocLinDtoActiva")
    val docLinDtoActiva: String,

    @SerializedName("DocLinDtoPolEsManual")
    val docLinDtoPolEsManual: String
)