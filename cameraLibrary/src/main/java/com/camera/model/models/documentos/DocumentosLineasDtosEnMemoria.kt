package com.camera.model.models.documentos

import com.camera.model.models.entities.docuents.DocumentosLineasDtosEntity
import com.google.gson.annotations.SerializedName

data class DocumentosLineasDtosEnMemoria(
    @SerializedName("EmpId")
    var empID: Long = 0,

    @SerializedName("TpoDocId")
    var tpoDocID: String = "",

    @SerializedName("DocUsuId")
    var docUsuID: String = "",

    @SerializedName("DocId")
    var docID: String = "",

    @SerializedName("DocLinId")
    var docLinID: Int = 0,

    @SerializedName("DocLinPolId")
    var docLinPolID: String = "",

    @SerializedName("DocLinDtoOrd")
    var docLinDtoOrd: Long = 0,

    @SerializedName("DocLinDtoPrc")
    var docLinDtoPrc: Double = 0.0,

    @SerializedName("DocLinDtoImp")
    var docLinDtoImp: Double = 0.0,

    @SerializedName("DocLinModAp")
    var docLinModAp: String = "",

    @SerializedName("PolId")
    var polID: String = "",

    @SerializedName("EscVig")
    var escVig: String = "",

    @SerializedName("modDT")
    var modDT: String = "",

    @SerializedName("DocLinDtoActiva")
    var docLinDtoActiva: String = "",

    @SerializedName("DocLinDtoPolEsManual")
    var docLinDtoPolEsManual: String = "",

    @SerializedName("DocIdMobile")
    var docIdMobile: String = ""
) {
    fun toDocumentoLineasDtosEntity() = DocumentosLineasDtosEntity(
        empID,
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
    fun toDocumentoLineasDtos() = DocumentosLineasDtos(
        empID,
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
        docLinDtoPolEsManual
    )
}