package com.camera.model.models.entities.docuents

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.camera.model.models.documentos.DocumentoPendiente
import com.camera.model.models.documentos.DocumentoPendienteEnMemoria

@Entity(
    tableName = "documentos_pendientes",
    primaryKeys = ["EmpId", "CliId", "CliLocId", "DPInterno"]
)
data class DocumentoPendienteEntity(
    @ColumnInfo(name = "EmpId")
    val empID: Long,

    @ColumnInfo(name = "CliId")
    val cliID: String,

    @ColumnInfo(name = "CliLocId")
    val cliLOCID: String,

    @ColumnInfo(name = "DPInterno")
    val dpInterno: String,

    @ColumnInfo(name = "TpoDocId")
    val tpoDocID: String,

    @ColumnInfo(name = "DocUsuId")
    val docUsuID: String,

    @ColumnInfo(name = "DocId")
    val docID: String,

    @ColumnInfo(name = "DPDocSerie")
    val dpDocSerie: String,

    @ColumnInfo(name = "DPDocNro")
    val dpDocNro: String,

    @ColumnInfo(name = "DPDocNroOriginal")
    val dpDocNroOriginal: String,

    @ColumnInfo(name = "DPDocFechaEmi")
    val dpDocFechaEmi: String,

    @ColumnInfo(name = "DPDocVto")
    val dpDocVto: String,

    @ColumnInfo(name = "DPDocRUT")
    val dpDocRUT: String,

    @ColumnInfo(name = "DPDocCI")
    val dpDocCI: String,

    @ColumnInfo(name = "DPMonedaId")
    val dpMonedaID: String,

    @ColumnInfo(name = "DPDocTipoCamb")
    val dpDocTipoCamb: Double,

    @ColumnInfo(name = "DPDocImporte")
    val dpDocImporte: Double,

    @ColumnInfo(name = "DPDocImpSaldo")
    var dpDocImpSaldo: Double,

    @ColumnInfo(name = "DPDocImpBase")
    val dpDocImpBase: Double,

    @ColumnInfo(name = "DPDocImpSaldoBase")
    val dpDocImpSaldoBase: Double,

    @ColumnInfo(name = "DPDias_Atraso")
    val dpDiasAtraso: String,

    @ColumnInfo(name = "DPDocSerieOriginal")
    val dpDocSerieOriginal: String,

    @ColumnInfo(name = "modDT")
    val modDT: String,

    @ColumnInfo(name = "DPVenId")
    val dpVenID: String,

    @ColumnInfo(name = "DPVenNombre")
    val dpVenNombre: String,

    @ColumnInfo(name = "DPActivo")
    var dpActivo: String,

    @ColumnInfo(name = "DPDocSyncFlg")
    var dpDocSyncFlg: String,

    @ColumnInfo(name = "DPCliIdOriginal")
    var dpCliIdOriginal: String,

//    @Ignore
//    var isSelected: Boolean = false
) {
    fun toDocumentoPendienteEnMemoria() = DocumentoPendienteEnMemoria(
        empID,
        cliID,
        cliLOCID,
        dpInterno,
        tpoDocID,
        docUsuID,
        docID,
        dpDocSerie,
        dpDocNro,
        dpDocNroOriginal,
        dpDocFechaEmi,
        dpDocVto,
        dpDocRUT,
        dpDocCI,
        dpMonedaID,
        dpDocTipoCamb,
        dpDocImporte,
        dpDocImpSaldo,
        dpDocImpBase,
        dpDocImpSaldoBase,
        dpDiasAtraso,
        dpDocSerieOriginal,
        modDT,
        dpVenID,
        dpVenNombre,
        dpActivo,
        dpDocSyncFlg,
        dpCliIdOriginal
    )

    fun toDocumentoPendiente() = DocumentoPendiente(
        empID,
        cliID,
        cliLOCID,
        dpInterno,
        tpoDocID,
        docUsuID,
        docID,
        dpDocSerie,
        dpDocNro,
        dpDocNroOriginal,
        dpDocFechaEmi,
        dpDocVto,
        dpDocRUT,
        dpDocCI,
        dpMonedaID,
        dpDocTipoCamb,
        dpDocImporte,
        dpDocImpSaldo,
        dpDocImpBase,
        dpDocImpSaldoBase,
        dpDiasAtraso,
        dpDocSerieOriginal,
        modDT,
        dpVenID,
        dpVenNombre,
        dpActivo,
        dpDocSyncFlg,
        dpCliIdOriginal
    )
}
