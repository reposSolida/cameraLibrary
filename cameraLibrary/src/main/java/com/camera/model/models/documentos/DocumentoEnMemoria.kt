package com.camera.model.models.documentos

import com.camera.model.models.entities.docuents.DocumentoEntity
import com.camera.model.models.entities.docuents.DocumentosLineasEntity
import com.google.gson.annotations.SerializedName

data class DocumentoEnMemoria(
    @SerializedName("EmpId")
    var empID: Long = 13L,

    @SerializedName("TpoDocId")
    var tpoDocID: String = "",

    @SerializedName("DocUsuId")
    var docUsuID: String = "",

    @SerializedName("DocId")
    var docID: String = "",

    @SerializedName("DocNro")
    var docNro: String = "",

    @SerializedName("DocSerie")
    var docSerie: String = "",

    @SerializedName("DocFHIns")
    var docFHIns: String = "",

    @SerializedName("DocFechaEmi")
    var docFechaEmi: String = "",

    @SerializedName("DocFechaEntrega")
    var docFechaEntrega: String = "",

    @SerializedName("DocRUT")
    var docRUT: String = "",

    @SerializedName("DocTitNom")
    var docTitNom: String = "",

    @SerializedName("DocTitRazSoc")
    var docTitRazSoc: String = "",

    @SerializedName("DocTitDir")
    var docTitDir: String = "",

    @SerializedName("DocTitTel")
    var docTitTel: String = "",

    @SerializedName("DocTitObs")
    var docTitObs: String = "",

    @SerializedName("DocConsFin")
    var docConsFin: String = "",

    @SerializedName("DocVto")
    var docVto: String = "",

    @SerializedName("MonedaId")
    var monedaID: String = "",

    @SerializedName("DocTipoCamb")
    var docTipoCamb: Double = 0.0,

    @SerializedName("DocImpSDSI")
    var docImpSDSI: Double = 0.0,

    @SerializedName("DocImpCDSI")
    var docImpCDSI: Double = 0.0,

    @SerializedName("DocImpCDCI")
    var docImpCDCI: Double = 0.0,

    @SerializedName("CliId")
    var cliID: String = "",

    @SerializedName("CliLocId")
    var cliLOCID: String = "",

    @SerializedName("modDT")
    var modDT: String = "",

    @SerializedName("DocActivo")
    var docActivo: String = "",

    @SerializedName("DocSyncFlg")
    var docSyncFlg: String = "",

    @SerializedName("DocSyncDT")
    var docSyncDT: String? = "",

    @SerializedName("DocSyncIntentos")
    var docSyncIntentos: Long = 0L,

    @SerializedName("DocNroOriginal")
    var docNroOriginal: String = "",

    @SerializedName("DocCI")
    var docCI: String = "",

    @SerializedName("DocTipoIdent")
    var docTipoIdent: String = "",

    @SerializedName("DocIdMobile")
    var docIDMobile: String = "",

    @SerializedName("DocCondVtaId")
    var docCondVtaID: String = "",

    @SerializedName("DocCondVtaDsc")
    var docCondVtaDsc: String = "",

    @SerializedName("DocOrdenDeCompra")
    var docOrdenDeCompra: String = "",

    @SerializedName("DocSerieOriginal")
    var docSerieOriginal: String = "",

    @SerializedName("DocFlagRel")
    var docFlagRel: String = "",

    @SerializedName("DocAnulado")
    var docAnulado: String = "",

    @SerializedName("DocFacturado")
    var docFacturado: String = "",

    @SerializedName("DocSyncError")
    var docSyncError: String = "",

    @SerializedName("DocRelPrimTpoDocId")
    var docRelPrimTpoDocID: String = "",

    @SerializedName("DocRelPrimDocUsuId")
    var docRelPrimDocUsuID: String = "",

    @SerializedName("DocRelPrimDocId")
    var docRelPrimDocID: String = "",

    @SerializedName("DocFlagSaldoMes")
    var docFlagSaldoMes: String = "",

    @SerializedName("DocFlgEstado")
    var docFlgEstado: String = "",

    @SerializedName("DocFlgRetira")
    var docFlgRetira: String = "",

    @SerializedName("DocDepId")
    var docDepID: String = "",

    @SerializedName("DocDepId_OfcCom")
    var docDepIDOfcCOM: String = "",

    @SerializedName("DocVenId")
    var docVenID: String = "",

    @SerializedName("DocVenId_Sup1")
    var docVenIDSup1: String = "",

    @SerializedName("DocVenId_Sup2")
    var docVenIDSup2: String = "",

    @SerializedName("DocVenId_Sup3")
    var docVenIDSup3: String = "",

    @SerializedName("DocVenId_Sup4")
    var docVenIDSup4: String = "",

    @SerializedName("DocVenId_Sup5")
    var docVenIDSup5: String = "",

    @SerializedName("DocVenId_Sup6")
    var docVenIDSup6: String = "",

    @SerializedName("DocVenId_Sup7")
    var docVenIDSup7: String = "",

    @SerializedName("DocVenId_Sup8")
    var docVenIDSup8: String = "",

    @SerializedName("DocVenId_Sup9")
    var docVenIDSup9: String = "",

    @SerializedName("DocPlaNro")
    var docPlaNro: String = "",

    @SerializedName("DocRepId")
    var docRepID: String = "",

    @SerializedName("DocFlgReparto")
    var docFlgReparto: String = "",

    @SerializedName("DocFlgExportacion")
    var docFlgExportacion: String = "",

    @SerializedName("DocPolEsManual")
    var docPolEsManual: String = "",

    @SerializedName("DocValidaStock")
    var docValidaStock: String = "",

    @SerializedName("DocValidaVenta")
    var docValidaVenta: String = "",

    @SerializedName("DocValidaCredito")
    var docValidaCredito: String = "",

    @SerializedName("DocDtoCabezal")
    var docDtoCabezal: Double = 0.0,

    @SerializedName("DocDtoCabezalExcluyente")
    var docDtoCabezalExcluyente: String = "",

    @SerializedName("DocMotDevId")
    var docMotDevID: String = "",

    @SerializedName("DocCliLocDir")
    var docCLILOCDir: String = "",

    @SerializedName("DocRefExterna")
    var docRefExterna: String = "",

    @SerializedName("DocBultos")
    var docBultos: Long = 0L,

    @SerializedName("DocPayment_Id")
    var docPaymentID: String = "",

    @SerializedName("DocMotRet")
    var docMotRet: String? = "",


    @SerializedName("Lineas")
    var lineas: MutableList<DocumentosLineasEnMemoria> = mutableListOf(),

    val orderType: String? = "",

    val prodIds: String? = "",

    @SerializedName("DocLat")
    var latitude: String = "",

    @SerializedName("DocLong")
    var longitude: String = "",

    var prodGrpProdCombo : MutableList<Pair<String, String>> = mutableListOf()//prodGrpProdCombo y presentProdId
) {

    fun docLines(): MutableList<DocumentosLineasEnMemoria> {
        if (empID == null || tpoDocID == null || docID == null || docIDMobile == null) {
            emptyList<DocumentosLineasEntity>()
        }
//        if (lineas == null || lineas!!.size == 0 || fuerzoDB) {
//            val documentoLineaDS = DocumentoLineaDS()
//            setLineas(
//                documentoLineaDS.getAllDocumentosLineasPorDocumento(
//                    empId,
//                    tpoDocId,
//                    docUsuId,
//                    docId,
//                    docIdMobile
//                )
//            )
        // }
        return lineas
    }

    fun getUltimoDocLinId(): Int {
        var ultimoNum = 0
        for (dl in lineas) {
            if (ultimoNum < dl.docLinID) ultimoNum = dl.docLinID
        }
        return ultimoNum
    }

    fun getSDSI(): Double {
        var ret = 0.0

        // retorno del cabezal
        if (docImpSDSI != 0.0) return docImpSDSI

        if (lineas == null) return ret
        for (dl in lineas!!) {
            ret += dl.docLinImpSDSI
        }
        docImpSDSI = ret
        return ret
    }

    fun getCDSI(): Double {
        var ret = 0.0

        // retorno del cabezal
        if (docImpCDSI != 0.0) return docImpCDSI

        // voy a las lineas
        if (lineas == null) return ret
        for (dl in lineas!!) {
            ret += dl.docLinImpCDSI
        }
        docImpCDSI = ret
        return ret
    }

    fun getCDCI(): Double {
        var ret = 0.0

        // retorno del cabezal
        if (docImpCDCI != 0.0) return docImpCDCI

        // voy a las lineas
        if (lineas == null) return ret
        for (dl in lineas!!) {
            ret += dl.docLinImpCDCI
        }
        docImpCDCI = ret
        return ret
    }

    fun getMaxDocLinId(): Int {
        var maxDocLinId = 0
        for (dl in lineas!!) {
            if (dl.docLinID > maxDocLinId) maxDocLinId = dl.docLinID.toInt()
        }
        return maxDocLinId
    }

    fun toDocumentoEntity(
        duplicated: String? = this.orderType,
        prodIds: String? = this.prodIds
    ) = DocumentoEntity(
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
        modDT,
        docActivo,
        docSyncFlg,
        docSyncDT!!,
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
        docFlgExportacion = docFlgExportacion,
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
        orderType = duplicated,
        prodIds = prodIds,
        latitude = latitude,
        longitude = longitude
    )
}

