package com.camera.model.models.documentos

import com.camera.model.models.entities.docuents.DocumentosLineasImpEntity
import com.google.gson.annotations.SerializedName

data class DocumentosLineasImp(
    @SerializedName("EmpId")
    val empID: Long,

    @SerializedName("TpoDocId")
    val tpoDocID: String,

    @SerializedName("DocUsuId")
    val docUsuID: String,

    @SerializedName("DocId")
    var docID: String,

    @SerializedName("DocLinId")
    val docLinID: Long,

    @SerializedName("ImpId")
    val impID: String,

    @SerializedName("DocLinImpPrc")
    val docLinImpPrc: Long,

    @SerializedName("DocLinImpOrd")
    val docLinImpOrd: Long,

    @SerializedName("DocLinImp")
    val docLinImp: Double,

    @SerializedName("DocLinImpModDT")
    val modDT: String?,

    @SerializedName("DocLinImpActiva")
    val docLinImpActiva: String
) {
    fun toEntity(docIdMobile: String) = DocumentosLineasImpEntity(
        empID,
        tpoDocID,
        docUsuID,
        docID,
        docLinID,
        impID,
        docLinImpPrc,
        docLinImpOrd,
        docLinImp,
        modDT ?: "",
        docLinImpActiva,
        docIdMobile
    )
}