package com.camera.model.models.documentos

import com.camera.model.models.entities.docuents.DocumentoPendienteEntity

data class DocumentoPendienteEnMemoria(

    var empID: Long = 0L,


    var cliID: String = "",

    var cliLOCID: String = "",

    var dpInterno: String = "",

    var tpoDocID: String = "",

    var docUsuID: String = "",

    var docID: String = "",

    var dpDocSerie: String = "",

    var dpDocNro: String = "",

    var dpDocNroOriginal: String = "",

    var dpDocFechaEmi: String = "",

    var dpDocVto: String = "",

    var dpDocRUT: String = "",

    var dpDocCI: String = "",

    var dpMonedaID: String = "",

    var dpDocTipoCamb: Double = 0.0,

    var dpDocImporte: Double = 0.0,

    var dpDocImpSaldo: Double = 0.0,

    var dpDocImpBase: Double = 0.0,

    var dpDocImpSaldoBase: Double = 0.0,

    var dpDiasAtraso: String = "",

    var dpDocSerieOriginal: String = "",

    var modDT: String = "",

    var dpVenID: String = "",

    var dpVenNombre: String = "",

    var dpActivo: String = "",

    var dpDocSyncFlg: String = "",

    var dpCliIdOriginal: String = "",

    var isSelected: Boolean = false
) {
    fun toDocumentoPendienteEntity() = DocumentoPendienteEntity(
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