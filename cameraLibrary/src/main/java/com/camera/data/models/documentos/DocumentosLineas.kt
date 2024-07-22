package com.camera.data.models.documentos

import com.google.gson.annotations.SerializedName

data class DocumentosLineas(
    @SerializedName("EmpId")
    val empID: Long,

    @SerializedName("TpoDocId")
    val tpoDocID: String,

    @SerializedName("DocUsuId")
    val docUsuID: String,

    @SerializedName("DocId")
    val docID: String,

    @SerializedName("DocLinId")
    val docLinID: Int,

    @SerializedName("ProdId")
    val prodID: String,

    @SerializedName("DocLinUnimedId")
    val docLinUnimedID: String,

    @SerializedName("DocLinUnimedDsc")
    val docLinUnimedDsc: String,

    @SerializedName("DocLinCnt")
    val docLinCnt: Double,

    @SerializedName("DocLisPreId")
    val docLisPreID: String,

    @SerializedName("DocLisPreDsc")
    val docLisPreDsc: String,

    @SerializedName("DocLinPrcUniSDSI")
    val docLinPrcUniSDSI: Double,

    @SerializedName("DocLinImpSDSI")
    val docLinImpSDSI: Double,

    @SerializedName("DocLinImpCDSI")
    val docLinImpCDSI: Double,

    @SerializedName("DocLinImpCDCI")
    val docLinImpCDCI: Double,

    @SerializedName("DocLinMotDevId")
    val docLinMotDevID: String,

    @SerializedName("DocLinCntEnt")
    val docLinCntEnt: Double,

    @SerializedName("DocLinCntPend")
    val docLinCntPend: Double,

    @SerializedName("DocLinBon")
    val docLinBon: String,

    @SerializedName("DocLinTipo")
    val docLinTipo: String,

    @SerializedName("DocLinDetalle")
    val docLinDetalle: String,

    @SerializedName("DocLinDTIns")
    val docLinDTIns: String,

    @SerializedName("DocLinModDT")
    val modDT: String,

    @SerializedName("DocLinActiva")
    val docLinActiva: String,

    @SerializedName("DocLinPolEsManual")
    val docLinPolEsManual: String,

//    @SerializedName("DocLinPorDto")
//    val docLinPorDto: Long,

    @SerializedName("DocLinPadre")
    val docLinPadre: Int,

    @SerializedName("DocLinHijo")
    val docLinHijo: Int,

    @SerializedName("DocLinBultos")
    val docLinBultos: Double,

    @SerializedName("DocLinRefExterna")
    val docLinRefExterna: String,

    @SerializedName("DocLinCodBarras")
    val docLinCodBarras: String,

    @SerializedName("LinImpuestos")
    val linImpuestos: List<DocumentosLineasImp>,

    val linDto: List<DocumentosLineasDtos>

)