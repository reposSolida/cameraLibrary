package com.camera.model.models.documentos

import androidx.room.ColumnInfo
import com.camera.model.models.entities.docuents.DocumentosLineasImpEntity

data class DocumentosLineasImpEnMemoria(
    @ColumnInfo(name = "EmpId")
    var empID: Int = 0,

    @ColumnInfo(name = "TpoDocId")
    var tpoDocID: String = "",

    @ColumnInfo(name = "DocUsuId")
    var docUsuID: String = "",

    @ColumnInfo(name = "DocId")
    var docID: String = "",

    @ColumnInfo(name = "DocLinId")
    var docLinID: Long = 0L,

    @ColumnInfo(name = "ImpId")
    var impID: String = "",

    @ColumnInfo(name = "DocLinImpPrc")
    var docLinImpPrc: Long,

    @ColumnInfo(name = "DocLinImpOrd")
    var docLinImpOrd: Long,

    @ColumnInfo(name = "DocLinImp")
    var docLinImp: Double,

    @ColumnInfo(name = "modDT")
    var modDT: String = "",

    @ColumnInfo(name = "DocLinImpActiva")
    var docLinImpActiva: String = "",

    @ColumnInfo(name = "DocIdMobile")
    var docIdMobile: String = ""
) {
    fun toDocumentosLineasImpEntity() = DocumentosLineasImpEntity(
        empID.toLong(),
        tpoDocID,
        docUsuID,
        docID,
        docLinID,
        impID,
        docLinImpPrc,
        docLinImpOrd,
        docLinImp,
        modDT,
        docLinImpActiva,
        docIdMobile
    )
}