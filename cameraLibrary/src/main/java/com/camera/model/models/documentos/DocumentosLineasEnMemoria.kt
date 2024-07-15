package com.camera.model.models.documentos

import com.camera.model.models.entities.docuents.DocumentosLineasEntity
import com.camera.model.models.entities.ProductoEntity
import com.google.gson.annotations.SerializedName

data class DocumentosLineasEnMemoria(
    @SerializedName("EmpId")
    var empID: Long = 13L,

    @SerializedName("TpoDocId")
    var tpoDocID: String = "",

    @SerializedName("DocUsuId")
    var docUsuID: String = "",

    @SerializedName("DocId")
    var docID: String = "",

    @SerializedName("DocLinId")
    var docLinID: Int = 0,

    @SerializedName("ProdId")
    var prodID: String = "",

    @SerializedName("DocLinUnimedId")
    var docLinUnimedID: String = "",

    @SerializedName("DocLinUnimedDsc")
    var docLinUnimedDsc: String = "",

    @SerializedName("DocLinCnt")
    var docLinCnt: Double = 0.0,

    @SerializedName("DocLisPreId")
    var docLisPreID: String = "",

    @SerializedName("DocLisPreDsc")
    var docLisPreDsc: String = "",

    @SerializedName("DocLinPrcUniSDSI")
    var docLinPrcUniSDSI: Double = 0.0,

    @SerializedName("DocLinImpSDSI")
    var docLinImpSDSI: Double = 0.0,

    @SerializedName("DocLinImpCDSI")
    var docLinImpCDSI: Double = 0.0,

    @SerializedName("DocLinImpCDCI")
    var docLinImpCDCI: Double = 0.0,

    @SerializedName("DocLinMotDevId")
    var docLinMotDevID: String = "",

    @SerializedName("DocLinCntEnt")
    var docLinCntEnt: Double = 0.0,

    @SerializedName("DocLinCntPend")
    var docLinCntPend: Double = 0.0,

    @SerializedName("DocLinBon")
    var docLinBon: String = "",

    @SerializedName("DocLinTipo")
    var docLinTipo: String = "",

    @SerializedName("DocLinDetalle")
    var docLinDetalle: String = "",

    @SerializedName("DocLinDTIns")
    var docLinDTIns: String = "",

    @SerializedName("DocLinModDT")
    var modDT: String? = "",

    @SerializedName("DocLinActiva")
    var docLinActiva: String = "",

    @SerializedName("DocLinPolEsManual")
    var docLinPolEsManual: String = "",

    @SerializedName("DocLinPorDto")
    var docLinPorDto: Long = 0,

    @SerializedName("DocLinPadre")
    var docLinPadre: Int = 0,

    @SerializedName("DocLinHijo")
    var docLinHijo: Int = 0,

    @SerializedName("DocLinBultos")
    var docLinBultos: Double = 0.0,

    @SerializedName("DocLinRefExterna")
    var docLinRefExterna: String = "",

    @SerializedName("DocLinCodBarras")
    var docLinCodBarras: String = "",

    @SerializedName("DocIdMobile")
    var docIdMobile: String = "",

    @SerializedName("DocLinFlgPrecioDigitado")
    var docLinFlgPrecioDigitado: String? = "",

    @SerializedName("DocLinDepId")
    var docLinDepId: String? = "",

    @SerializedName("DocLinPrcUniListaSDSI")
    var docLinPrcUniListaSDSI: Double? = 0.0,

    @SerializedName("LinImpuestos")
    var linImpuestos: MutableList<DocumentosLineasImp> = mutableListOf(),

    @SerializedName("linDto")
    var linDescuentos: MutableList<DocumentosLineasDtos> = mutableListOf(),

    var prodGrpProdCombo: String = "",

    var producto: ProductoEntity? = null,

//    var combosProductLines: MutableList<ProductoEntity> = mutableListOf()

) {

    fun docLinDescuentos(): MutableList<DocumentosLineasDtosEnMemoria> {
        if (empID == null || tpoDocID == null || docID == null) {
            // antes preguntaba ademas por " || docIdMobile == null", pero se saca
            return mutableListOf()
        }

        // si sigue siendo null armo uno nuevo
        return linDescuentos.map { it.toEntity(docIdMobile).toDocumentoLineasDtosEnMemoria() }
            .toMutableList()
    }

    fun docLinImpuestos(): List<DocumentosLineasImpEnMemoria> {
        if (empID == null || tpoDocID == null || docID == null) {
            // antes preguntaba ademas por " || docIdMobile == null", pero se saca
            return emptyList()
        }

        // si sigue siendo null armo uno nuevo
        return if (linImpuestos == null) emptyList() else linImpuestos.map {
            it.toEntity(docIdMobile).toDocumentosLineasImpEnMemoria()
        }
    }

    fun toDocumentoLineaEntity() = DocumentosLineasEntity(
        if (empID.toInt() != 0) empID else docID.substringBefore("-").toLong(),
        tpoDocID,
        docUsuID,
        docID,
        docLinID,
        prodID,
        docLinUnimedID,
        docLinUnimedDsc,
        docLinCnt,
        docLisPreID,
        docLisPreDsc,
        docLinPrcUniSDSI,
        docLinImpSDSI,
        docLinImpCDSI,
        docLinImpCDCI,
        docLinMotDevID,
        docLinCntEnt,
        docLinCntPend,
        docLinBon,
        docLinTipo,
        docLinDetalle,
        docLinDTIns,
        modDT,
        docLinActiva,
        docLinPolEsManual,
        docLinPorDto,
        docLinPadre,
        docLinHijo,
        docLinBultos,
        docLinRefExterna,
        docLinCodBarras,
        docIdMobile,
        docLinFlgPrecioDigitado,
        docLinDepId,
        docLinPrcUniListaSDSI,
        prodGrpProdCombo = prodGrpProdCombo,
    )
}
//    var docLinPrcUniListaSDSI: Double = 0.0,
//
//    var docLinFlgPrecioDigitado: String? = null,
//
//    var docLinDepId: String? = null,
//
//    private var linDescuentos: MutableList<DocumentosLineasDtosEntity>? = mutableListOf()

//    fun getLinDescuentosSoloMemoria(): MutableList<DocumentosLineasDtosEntity>? {
//        if (empID == null || tpoDocID == null || docID == null) {
//            linDescuentos?.clear()
//        }
//        if (linDescuentos == null || linDescuentos?.size == 0) {
//            linDescuentos?.clear()
//        }
//
//        // si sigue siendo null armo uno nuevo
//        if (linDescuentos == null) {
//            linDescuentos?.clear()
//        }
//        return linDescuentos
//    }
