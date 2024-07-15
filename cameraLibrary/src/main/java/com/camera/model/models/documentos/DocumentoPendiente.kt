package com.camera.model.models.documentos

import com.camera.model.models.entities.docuents.DocumentoPendienteEntity
import com.google.gson.annotations.SerializedName

data class DocumentoPendiente(
    @SerializedName("EmpId")
    val empID: Long,

    @SerializedName("CliId")
    val cliID: String,

    @SerializedName("CliLocId")
    val cliLOCID: String,

    @SerializedName("DPInterno")
    val dpInterno: String,

    @SerializedName("TpoDocId")
    val tpoDocID: String,

    @SerializedName("DocUsuId")
    val docUsuID: String,

    @SerializedName("DocId")
    val docID: String,

    @SerializedName("DPDocSerie")
    val dpDocSerie: String,

    @SerializedName("DPDocNro")
    val dpDocNro: String,

    @SerializedName("DPDocNroOriginal")
    val dpDocNroOriginal: String,

    @SerializedName("DPDocFechaEmi")
    val dpDocFechaEmi: String,

    @SerializedName("DPDocVto")
    val dpDocVto: String,

    @SerializedName("DPDocRUT")
    val dpDocRUT: String,

    @SerializedName("DPDocCI")
    val dpDocCI: String,

    @SerializedName("DPMonedaId")
    val dpMonedaID: String,

    @SerializedName("DPDocTipoCamb")
    val dpDocTipoCamb: Double,

    @SerializedName("DPDocImporte")
    val dpDocImporte: Double,

    @SerializedName("DPDocImpSaldo")
    val dpDocImpSaldo: Double,

    @SerializedName("DPDocImpBase")
    val dpDocImpBase: Double,

    @SerializedName("DPDocImpSaldoBase")
    val dpDocImpSaldoBase: Double,

    @SerializedName("DPDias_Atraso")
    val dpDiasAtraso: String,

    @SerializedName("DPDocSerieOriginal")
    val dpDocSerieOriginal: String,

    @SerializedName("DPModDt")
    var dpModDt: String,

    @SerializedName("DPVenId")
    val dpVenID: String,

    @SerializedName("DPVenNombre")
    val dpVenNombre: String,

    @SerializedName("DPActivo")
    val dpActivo: String,

    @SerializedName("DPDocSyncFlg")
    var dpDocSyncFlg: String,

    @SerializedName("DPCliIdOriginal")
    var dpCliIdOriginal: String? =""
) {
    fun toEntity() = DocumentoPendienteEntity(
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
        dpModDt,
        dpVenID,
        dpVenNombre,
        dpActivo,
        dpDocSyncFlg,
        dpCliIdOriginal.orEmpty()
    )
}