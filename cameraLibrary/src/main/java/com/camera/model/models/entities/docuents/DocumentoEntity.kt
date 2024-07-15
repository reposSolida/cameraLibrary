package com.camera.model.models.entities.docuents

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.camera.model.models.documentos.DocumentoEnMemoria
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "documentos",
    primaryKeys = ["EmpId", "TpoDocId", "DocUsuId", "DocId", "DocIdMobile"]
)
data class DocumentoEntity(
    @ColumnInfo(name = "EmpId")
    val empID: Long,

    @ColumnInfo(name = "TpoDocId")
    val tpoDocID: String,

    @ColumnInfo(name = "DocUsuId")
    val docUsuID: String,

    @ColumnInfo(name = "DocId")
    val docID: String,

    @ColumnInfo(name = "DocNro")
    val docNro: String,

    @ColumnInfo(name = "DocSerie")
    val docSerie: String,

    @ColumnInfo(name = "DocFHIns")
    val docFHIns: String,

    @ColumnInfo(name = "DocFechaEmi")
    val docFechaEmi: String,

    @ColumnInfo(name = "DocFechaEntrega")
    val docFechaEntrega: String,

    @ColumnInfo(name = "DocRUT")
    val docRUT: String,

    @ColumnInfo(name = "DocTitNom")
    val docTitNom: String,

    @ColumnInfo(name = "DocTitRazSoc")
    val docTitRazSoc: String,

    @ColumnInfo(name = "DocTitDir")
    val docTitDir: String,

    @ColumnInfo(name = "DocTitTel")
    val docTitTel: String,

    @ColumnInfo(name = "DocTitObs")
    val docTitObs: String,

    @ColumnInfo(name = "DocConsFin")
    val docConsFin: String,

    @ColumnInfo(name = "DocVto")
    val docVto: String,

    @ColumnInfo(name = "MonedaId")
    val monedaID: String,

    @ColumnInfo(name = "DocTipoCamb")
    val docTipoCamb: Double,

    @ColumnInfo(name = "DocImpSDSI")
    val docImpSDSI: Double,

    @ColumnInfo(name = "DocImpCDSI")
    val docImpCDSI: Double,

    @ColumnInfo(name = "DocImpCDCI")
    val docImpCDCI: Double,

    @ColumnInfo(name = "CliId")
    val cliID: String,

    @ColumnInfo(name = "CliLocId")
    val cliLOCID: String,

    @ColumnInfo(name = "modDT")
    var modDT: String?,

    @ColumnInfo(name = "DocActivo")
    val docActivo: String,

    @ColumnInfo(name = "DocSyncFlg")
    var docSyncFlg: String,

    @ColumnInfo(name = "DocSyncDT")
    val docSyncDT: String,

    @ColumnInfo(name = "DocSyncIntentos")
    val docSyncIntentos: Long,

    @ColumnInfo(name = "DocNroOriginal")
    val docNroOriginal: String,

    @ColumnInfo(name = "DocCI")
    val docCI: String,

    @ColumnInfo(name = "DocTipoIdent")
    val docTipoIdent: String,

    @ColumnInfo(name = "DocIdMobile")
    val docIDMobile: String,

    @ColumnInfo(name = "DocCondVtaId")
    val docCondVtaID: String,

    @ColumnInfo(name = "DocCondVtaDsc")
    val docCondVtaDsc: String,

    @ColumnInfo(name = "DocOrdenDeCompra")
    val docOrdenDeCompra: String,

    @ColumnInfo(name = "DocSerieOriginal")
    val docSerieOriginal: String,

    @ColumnInfo(name = "DocFlagRel")
    val docFlagRel: String,

    @ColumnInfo(name = "DocAnulado")
    val docAnulado: String,

    @ColumnInfo(name = "DocFacturado")
    val docFacturado: String,

    @ColumnInfo(name = "DocSyncError")
    var docSyncError: String,

    @ColumnInfo(name = "DocRelPrimTpoDocId")
    val docRelPrimTpoDocID: String,

    @ColumnInfo(name = "DocRelPrimDocUsuId")
    val docRelPrimDocUsuID: String,

    @ColumnInfo(name = "DocRelPrimDocId")
    val docRelPrimDocID: String,

    @ColumnInfo(name = "DocFlagSaldoMes")
    val docFlagSaldoMes: String,

    @ColumnInfo(name = "DocFlgEstado")
    var docFlgEstado: String,

    @ColumnInfo(name = "DocFlgRetira")
    val docFlgRetira: String,

    @ColumnInfo(name = "DocDepId")
    val docDepID: String,

    @ColumnInfo(name = "DocDepId_OfcCom")
    val docDepIDOfcCOM: String,

    @ColumnInfo(name = "DocVenId")
    val docVenID: String,

    @ColumnInfo(name = "DocVenId_Sup1")
    val docVenIDSup1: String,

    @ColumnInfo(name = "DocVenId_Sup2")
    val docVenIDSup2: String,

    @ColumnInfo(name = "DocVenId_Sup3")
    val docVenIDSup3: String,

    @ColumnInfo(name = "DocVenId_Sup4")
    val docVenIDSup4: String,

    @ColumnInfo(name = "DocVenId_Sup5")
    val docVenIDSup5: String,

    @ColumnInfo(name = "DocVenId_Sup6")
    val docVenIDSup6: String,

    @ColumnInfo(name = "DocVenId_Sup7")
    val docVenIDSup7: String,

    @ColumnInfo(name = "DocVenId_Sup8")
    val docVenIDSup8: String,

    @ColumnInfo(name = "DocVenId_Sup9")
    val docVenIDSup9: String,

    @ColumnInfo(name = "DocPlaNro")
    val docPlaNro: String,

    @ColumnInfo(name = "DocRepId")
    val docRepID: String,

    @ColumnInfo(name = "DocFlgReparto")
    val docFlgReparto: String,

    @ColumnInfo(name = "DocFlgExportacion")
    var docFlgExportacion: String,

    @ColumnInfo(name = "DocPolEsManual")
    val docPolEsManual: String,

    @ColumnInfo(name = "DocValidaStock")
    val docValidaStock: String,

    @ColumnInfo(name = "DocValidaVenta")
    val docValidaVenta: String,

    @ColumnInfo(name = "DocValidaCredito")
    var docValidaCredito: String,

    @ColumnInfo(name = "DocDtoCabezal")
    val docDtoCabezal: Double,

    @ColumnInfo(name = "DocDtoCabezalExcluyente")
    val docDtoCabezalExcluyente: String,

    @ColumnInfo(name = "DocMotDevId")
    val docMotDevID: String,

    @ColumnInfo(name = "DocCliLocDir")
    val docCLILOCDir: String,

    @ColumnInfo(name = "DocRefExterna")
    val docRefExterna: String,

    @ColumnInfo(name = "DocBultos")
    val docBultos: Long,

    @ColumnInfo(name = "DocPayment_Id")
    val docPaymentID: String,

    @ColumnInfo(name = "DocMotRet")
    val docMotRet: String?,

    @ColumnInfo(name = "OrderType")
    val orderType: String? = "",

    @ColumnInfo(name = "MissingProdIds")
    val prodIds: String? = "",

    @SerializedName("DocLat")
    var latitude: String = "",

    @SerializedName("DocLong")
    var longitude: String = ""
) {
    fun toDocumentoEnMemoria() = DocumentoEnMemoria(
        empID,
        tpoDocID,
        docUsuID,
        docID,
        docNro,
        docSerie,
        docFHIns,
        docFechaEmi,
        docFechaEntrega,
        docRUT,
        docTitNom,
        docTitRazSoc,
        docTitDir,
        docTitTel,
        docTitObs,
        docConsFin,
        docVto,
        monedaID,
        docTipoCamb,
        docImpSDSI,
        docImpCDSI,
        docImpCDCI,
        cliID,
        cliLOCID,
        modDT ?: "",
        docActivo,
        docSyncFlg,
        docSyncDT,
        docSyncIntentos,
        docNroOriginal,
        docCI,
        docTipoIdent,
        docIDMobile,
        docCondVtaID,
        docCondVtaDsc,
        docOrdenDeCompra,
        docSerieOriginal,
        docFlagRel,
        docAnulado,
        docFacturado,
        docSyncError,
        docRelPrimTpoDocID,
        docRelPrimDocUsuID,
        docRelPrimDocID,
        docFlagSaldoMes,
        docFlgEstado,
        docFlgRetira,
        docDepID,
        docDepIDOfcCOM,
        docVenID,
        docVenIDSup1,
        docVenIDSup2,
        docVenIDSup3,
        docVenIDSup4,
        docVenIDSup5,
        docVenIDSup6,
        docVenIDSup7,
        docVenIDSup8,
        docVenIDSup9,
        docPlaNro,
        docRepID,
        docFlgReparto,
        docFlgExportacion,
        docPolEsManual,
        docValidaStock,
        docValidaVenta,
        docValidaCredito,
        docDtoCabezal,
        docDtoCabezalExcluyente,
        docMotDevID,
        docCLILOCDir,
        docRefExterna,
        docBultos,
        docPaymentID,
        docMotRet,
        orderType = orderType,
        prodIds = prodIds,
        latitude = latitude,
        longitude = longitude
    )
}
