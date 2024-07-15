package com.camera.model.models.documentos

import com.camera.model.models.entities.docuents.DocumentoEntity
import com.google.gson.annotations.SerializedName

data class Documento(
    @SerializedName("EmpId")
    val empID: Long,

    @SerializedName("TpoDocId")
    val tpoDocID: String?,

    @SerializedName("DocUsuId")
    val docUsuID: String?,

    @SerializedName("DocId")
    val docID: String?,

    @SerializedName("DocNro")
    val docNro: String?,

    @SerializedName("DocSerie")
    val docSerie: String?,

    @SerializedName("DocFHIns")
    val docFHIns: String?,

    @SerializedName("DocFechaEmi")
    val docFechaEmi: String?,

    @SerializedName("DocFechaEntrega")
    val docFechaEntrega: String?,

    @SerializedName("DocRUT")
    val docRUT: String?,

    @SerializedName("DocTitNom")
    val docTitNom: String?,

    @SerializedName("DocTitRazSoc")
    val docTitRazSoc: String?,

    @SerializedName("DocTitDir")
    val docTitDir: String?,

    @SerializedName("DocTitTel")
    val docTitTel: String?,

    @SerializedName("DocTitObs")
    val docTitObs: String?,

    @SerializedName("DocConsFin")
    val docConsFin: String?,

    @SerializedName("DocVto")
    val docVto: String?,

    @SerializedName("MonedaId")
    val monedaID: String?,

    @SerializedName("DocTipoCamb")
    val docTipoCamb: Double,

    @SerializedName("DocImpSDSI")
    val docImpSDSI: Double,

    @SerializedName("DocImpCDSI")
    val docImpCDSI: Double,

    @SerializedName("DocImpCDCI")
    val docImpCDCI: Double,

    @SerializedName("CliId")
    val cliID: String?,

    @SerializedName("CliLocId")
    val cliLOCID: String?,

    @SerializedName("modDT")
    val modDT: String?,

    @SerializedName("DocActivo")
    val docActivo: String?,

    @SerializedName("DocSyncFlg")
    var docSyncFlg: String?,

    @SerializedName("DocSyncDT")
    val docSyncDT: String?,

    @SerializedName("DocSyncIntentos")
    val docSyncIntentos: Long,

    @SerializedName("DocNroOriginal")
    val docNroOriginal: String?,

    @SerializedName("DocCI")
    val docCI: String?,

    @SerializedName("DocTipoIdent")
    val docTipoIdent: String?,

    @SerializedName("DocIdMobile")
    val docIDMobile: String?,

    @SerializedName("DocCondVtaId")
    val docCondVtaID: String?,

    @SerializedName("DocCondVtaDsc")
    val docCondVtaDsc: String?,

    @SerializedName("DocOrdenDeCompra")
    val docOrdenDeCompra: String?,

    @SerializedName("DocSerieOriginal")
    val docSerieOriginal: String?,

    @SerializedName("DocFlagRel")
    val docFlagRel: String?,

    @SerializedName("DocAnulado")
    val docAnulado: String?,

    @SerializedName("DocFacturado")
    val docFacturado: String?,

    @SerializedName("DocSyncError")
    var docSyncError: String?,

    @SerializedName("DocRelPrimTpoDocId")
    val docRelPrimTpoDocID: String?,

    @SerializedName("DocRelPrimDocUsuId")
    val docRelPrimDocUsuID: String?,

    @SerializedName("DocRelPrimDocId")
    val docRelPrimDocID: String?,

    @SerializedName("DocFlagSaldoMes")
    val docFlagSaldoMes: String?,

    @SerializedName("DocFlgEstado")
    val docFlgEstado: String?,

    @SerializedName("DocFlgRetira")
    val docFlgRetira: String?,

    @SerializedName("DocDepId")
    val docDepID: String?,

    @SerializedName("DocDepId_OfcCom")
    val docDepIDOfcCOM: String?,

    @SerializedName("DocVenId")
    val docVenID: String?,

    @SerializedName("DocVenId_Sup1")
    val docVenIDSup1: String?,

    @SerializedName("DocVenId_Sup2")
    val docVenIDSup2: String?,

    @SerializedName("DocVenId_Sup3")
    val docVenIDSup3: String?,

    @SerializedName("DocVenId_Sup4")
    val docVenIDSup4: String?,

    @SerializedName("DocVenId_Sup5")
    val docVenIDSup5: String?,

    @SerializedName("DocVenId_Sup6")
    val docVenIDSup6: String?,

    @SerializedName("DocVenId_Sup7")
    val docVenIDSup7: String?,

    @SerializedName("DocVenId_Sup8")
    val docVenIDSup8: String?,

    @SerializedName("DocVenId_Sup9")
    val docVenIDSup9: String?,

    @SerializedName("DocPlaNro")
    val docPlaNro: String?,

    @SerializedName("DocRepId")
    val docRepID: String?,

    @SerializedName("DocFlgReparto")
    val docFlgReparto: String?,

    @SerializedName("DocFlgExportacion")
    val docFlgExportacion: String?,

    @SerializedName("DocPolEsManual")
    val docPolEsManual: String?,

    @SerializedName("DocValidaStock")
    val docValidaStock: String?,

    @SerializedName("DocValidaVenta")
    val docValidaVenta: String?,

    @SerializedName("DocValidaCredito")
    val docValidaCredito: String?,

    @SerializedName("DocDtoCabezal")
    val docDtoCabezal: Double,

    @SerializedName("DocDtoCabezalExcluyente")
    val docDtoCabezalExcluyente: String?,

    @SerializedName("DocMotDevId")
    val docMotDevID: String?,

    @SerializedName("DocCliLocDir")
    val docCLILOCDir: String?,

    @SerializedName("DocRefExterna")
    val docRefExterna: String?,

    @SerializedName("DocBultos")
    val docBultos: Long,

    @SerializedName("DocPayment_Id")
    val docPaymentID: String?,

    @SerializedName("DocMotRet")
    val docMotRet: String??,

    @SerializedName("Lineas")
    val lineas: List<DocumentosLineas>
) {
    fun toEntity() = DocumentoEntity(
        empID,
        tpoDocID.orEmpty(),
        docUsuID.orEmpty(),
        docID.orEmpty(),
        docNro.orEmpty(),
        docSerie.orEmpty(),
        docFHIns.orEmpty(),
        docFechaEmi.orEmpty(),
        docFechaEntrega.orEmpty(),
        docRUT.orEmpty(),
        docTitNom.orEmpty(),
        docTitRazSoc.orEmpty(),
        docTitDir.orEmpty(),
        docTitTel.orEmpty(),
        docTitObs.orEmpty(),
        docConsFin.orEmpty(),
        docVto.orEmpty(),
        monedaID.orEmpty(),
        docTipoCamb,
        docImpSDSI,
        docImpCDSI,
        docImpCDCI,
        cliID.orEmpty(),
        cliLOCID.orEmpty(),
        modDT,
        docActivo.orEmpty(),
        docSyncFlg.orEmpty(),
        docSyncDT.orEmpty(),
        docSyncIntentos,
        docNroOriginal.orEmpty(),
        docCI.orEmpty(),
        docTipoIdent.orEmpty(),
        docIDMobile.orEmpty(),
        docCondVtaID.orEmpty(),
        docCondVtaDsc.orEmpty(),
        docOrdenDeCompra.orEmpty(),
        docSerieOriginal.orEmpty(),
        docFlagRel.orEmpty(),
        docAnulado.orEmpty(),
        docFacturado.orEmpty(),
        docSyncError.orEmpty(),
        docRelPrimTpoDocID.orEmpty(),
        docRelPrimDocUsuID.orEmpty(),
        docRelPrimDocID.orEmpty(),
        docFlagSaldoMes.orEmpty(),
        docFlgEstado.orEmpty(),
        docFlgRetira.orEmpty(),
        docDepID.orEmpty(),
        docDepIDOfcCOM.orEmpty(),
        docVenID.orEmpty(),
        docVenIDSup1.orEmpty(),
        docVenIDSup2.orEmpty(),
        docVenIDSup3.orEmpty(),
        docVenIDSup4.orEmpty(),
        docVenIDSup5.orEmpty(),
        docVenIDSup6.orEmpty(),
        docVenIDSup7.orEmpty(),
        docVenIDSup8.orEmpty(),
        docVenIDSup9.orEmpty(),
        docPlaNro.orEmpty(),
        docRepID.orEmpty(),
        docFlgReparto.orEmpty(),
        docFlgExportacion.orEmpty(),
        docPolEsManual.orEmpty(),
        docValidaStock.orEmpty(),
        docValidaVenta.orEmpty(),
        docValidaCredito.orEmpty(),
        docDtoCabezal,
        docDtoCabezalExcluyente.orEmpty(),
        docMotDevID.orEmpty(),
        docCLILOCDir.orEmpty(),
        docRefExterna.orEmpty(),
        docBultos,
        docPaymentID.orEmpty(),
        docMotRet
    )
}
