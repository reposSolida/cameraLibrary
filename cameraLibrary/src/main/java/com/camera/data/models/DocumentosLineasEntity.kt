package com.camera.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 ** Created by Carlos A. Novaez Guerrero on 4/13/2023 10:01 AM
 ** cnovaez.dev@outlook.com
 **/
@Entity(
    tableName = "documentos_lineas",
    primaryKeys = ["EmpId", "TpoDocId", "DocUsuId", "DocId", "DocLinId", "DocIdMobile"]
)
data class DocumentosLineasEntity(
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

    @ColumnInfo(name = "ProdId")
    var prodID: String = "",

    @ColumnInfo(name = "DocLinUnimedId")
    var docLinUnimedID: String = "",

    @ColumnInfo(name = "DocLinUnimedDsc")
    var docLinUnimedDsc: String = "",

    @ColumnInfo(name = "DocLinCnt")
    var docLinCnt: Double = 0.0,

    @ColumnInfo(name = "DocLisPreId")
    var docLisPreID: String = "",

    @ColumnInfo(name = "DocLisPreDsc")
    var docLisPreDsc: String = "",

    @ColumnInfo(name = "DocLinPrcUniSDSI")
    var docLinPrcUniSDSI: Double = 0.0,

    @ColumnInfo(name = "DocLinImpSDSI")
    var docLinImpSDSI: Double = 0.0,

    @ColumnInfo(name = "DocLinImpCDSI")
    var docLinImpCDSI: Double = 0.0,

    @ColumnInfo(name = "DocLinImpCDCI")
    var docLinImpCDCI: Double = 0.0,

    @ColumnInfo(name = "DocLinMotDevId")
    var docLinMotDevID: String = "",

    @ColumnInfo(name = "DocLinCntEnt")
    var docLinCntEnt: Double = 0.0,

    @ColumnInfo(name = "DocLinCntPend")
    var docLinCntPend: Double = 0.0,

    @ColumnInfo(name = "DocLinBon")
    var docLinBon: String = "",

    @ColumnInfo(name = "DocLinTipo")
    var docLinTipo: String = "",

    @ColumnInfo(name = "DocLinDetalle")
    var docLinDetalle: String = "",

    @ColumnInfo(name = "DocLinDTIns")
    var docLinDTIns: String = "",

    @ColumnInfo(name = "modDT")
    var modDT: String? = "",

    @ColumnInfo(name = "DocLinActiva")
    var docLinActiva: String = "",

    @ColumnInfo(name = "DocLinPolEsManual")
    var docLinPolEsManual: String = "",

    @ColumnInfo(name = "DocLinPorDto")
    var docLinPorDto: Long = 0,

    @ColumnInfo(name = "DocLinPadre")
    var docLinPadre: Int = 0,

    @ColumnInfo(name = "DocLinHijo")
    var docLinHijo: Int = 0,

    @ColumnInfo(name = "DocLinBultos")
    var docLinBultos: Double = 0.0,

    @ColumnInfo(name = "DocLinRefExterna")
    var docLinRefExterna: String = "",

    @ColumnInfo(name = "DocLinCodBarras")
    var docLinCodBarras: String = "",

    @ColumnInfo(name = "DocIdMobile")
    var docIdMobile: String = "",

    @ColumnInfo(name = "DocLinFlgPrecioDigitado")
    var docLinFlgPrecioDigitado: String? = "",

    @ColumnInfo(name = "DocLinDepId")
    var docLinDepId: String? = "",

    @ColumnInfo(name = "DocLinPrcUniListaSDSI")
    var docLinPrcUniListaSDSI: Double? = 0.0,

    @ColumnInfo(name = "ProdGrpProdCombo")
    var prodGrpProdCombo: String = "",

    @ColumnInfo(name = "SyncFlag")
    var syncFlag: String = "P"
) {
}