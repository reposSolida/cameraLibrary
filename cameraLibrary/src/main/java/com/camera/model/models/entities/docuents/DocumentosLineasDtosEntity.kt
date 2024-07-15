package com.camera.model.models.entities.docuents

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.camera.model.models.documentos.DocumentosLineasDtosEnMemoria
import com.camera.model.models.documentos.DocumentosLineasDtos

@Entity(
    tableName = "documentos_lineas_dts",
    primaryKeys = ["EmpId", "TpoDocId", "DocUsuId", "DocId", "DocLinId", "DocIdMobile", "DocLinPolId"]
)
data class DocumentosLineasDtosEntity(
    @ColumnInfo(name = "EmpId")
    var empID: Long = 0,

    @ColumnInfo(name = "TpoDocId")
    var tpoDocID: String = "",

    @ColumnInfo(name = "DocUsuId")
    var docUsuID: String = "",

    @ColumnInfo(name = "DocId")
    var docID: String = "",

    @ColumnInfo(name = "DocLinId")
    var docLinID: Int = 0,

    @ColumnInfo(name = "DocLinPolId")
    var docLinPolID: String = "",

    @ColumnInfo(name = "DocLinDtoOrd")
    var docLinDtoOrd: Long = 0,

    @ColumnInfo(name = "DocLinDtoPrc")
    var docLinDtoPrc: Double = 0.0,

    @ColumnInfo(name = "DocLinDtoImp")
    var docLinDtoImp: Double = 0.0,

    @ColumnInfo(name = "DocLinModAp")
    var docLinModAp: String = "",

    @ColumnInfo(name = "PolId")
    var polID: String = "",

    @ColumnInfo(name = "EscVig")
    var escVig: String = "",

    @ColumnInfo(name = "modDT")
    var modDT: String? = "",

    @ColumnInfo(name = "DocLinDtoActiva")
    var docLinDtoActiva: String = "",

    @ColumnInfo(name = "DocLinDtoPolEsManual")
    var docLinDtoPolEsManual: String = "",

    @ColumnInfo(name = "DocIdMobile")
    var docIdMobile: String = ""
) {
    fun toDocumentoLineasDtosEnMemoria() = DocumentosLineasDtosEnMemoria(
        if (empID.toInt() != 0) empID else  docID.substringBefore("-").toLong(),
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
        modDT ?: "",
        docLinDtoActiva,
        docLinDtoPolEsManual,
        docIdMobile
    )
    fun toDocumentoLineasDtos() = DocumentosLineasDtos(
        if (empID.toInt() != 0) empID else  docID.substringBefore("-").toLong(),
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
        modDT ?: "",
        docLinDtoActiva,
        docLinDtoPolEsManual,
    )
}