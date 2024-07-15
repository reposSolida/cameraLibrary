package com.camera.model.models.documentos

import com.camera.model.models.entities.docuents.DocumentosLineasDtosEntity
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
) {
    fun toEntity(docIdMobile: String) = DocumentosLineasDtosEntity(
        if (empID.toInt() != 0) empID else docID.substringBefore("-").toLong(),
        tpoDocID,
        docUsuID,
        docID,
        docLinID,
        docLinPolID,
        docLinDtoOrd,
        docLinDtoPrc,
        docLinDtoImp,
        docLinModAp,
        polID,
        escVig,
        modDT,
        docLinDtoActiva,
        docLinDtoPolEsManual,
        docIdMobile
    )
}