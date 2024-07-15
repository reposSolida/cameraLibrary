package com.camera.model.models.entities.docuents

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.camera.model.models.documentos.DocumentosLineasImp
import com.camera.model.models.documentos.DocumentosLineasImpEnMemoria

@Entity(
    tableName = "documentos_lineas_imp",
    primaryKeys = ["EmpId", "TpoDocId", "DocUsuId", "DocId", "DocLinId", "DocIdMobile", "ImpId"]
)
data class DocumentosLineasImpEntity(
    @ColumnInfo(name = "EmpId")
    val empID: Long,

    @ColumnInfo(name = "TpoDocId")
    val tpoDocID: String,

    @ColumnInfo(name = "DocUsuId")
    val docUsuID: String,

    @ColumnInfo(name = "DocId")
    var docID: String,

    @ColumnInfo(name = "DocLinId")
    val docLinID: Long,

    @ColumnInfo(name = "ImpId")
    val impID: String,

    @ColumnInfo(name = "DocLinImpPrc")
    val docLinImpPrc: Long,

    @ColumnInfo(name = "DocLinImpOrd")
    val docLinImpOrd: Long,

    @ColumnInfo(name = "DocLinImp")
    val docLinImp: Double,

    @ColumnInfo(name = "modDT")
    val modDT: String?,

    @ColumnInfo(name = "DocLinImpActiva")
    val docLinImpActiva: String,

    @ColumnInfo(name = "DocIdMobile")
    var docIdMobile: String
) {
    fun toDocumentosLineasImpEnMemoria() = DocumentosLineasImpEnMemoria(
        empID.toInt(),
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

    fun toDocumentosLineasImp() = DocumentosLineasImp(
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
        docLinImpActiva
    )
}