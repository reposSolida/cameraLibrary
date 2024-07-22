package com.camera.data.models

import com.google.gson.annotations.SerializedName

data class Recibo(
    @SerializedName("EmpId")
    val empID: Long,

    @SerializedName("RecTpoDocId")
    val recTpoDocID: String,

    @SerializedName("RecDocId")
    val recDocID: String,

    @SerializedName("InstID")
    val instID: Long,

    @SerializedName("RecFchEmi")
    val recFchEmi: String?,

    @SerializedName("RecCliId")
    val recCLIID: String?,

    @SerializedName("RecCliNom")
    val recCLINom: String?,

    @SerializedName("RecSerieOriginal")
    val recSerieOriginal: String?,

    @SerializedName("RecNroOriginal")
    val recNroOriginal: String?,

    @SerializedName("RecFchIng")
    val recFchIng: String?,

    @SerializedName("RecModDT")
    val recModDT: String?,

    @SerializedName("RecEstado")
    val recEstado: String?,

    @SerializedName("RecCliLocId")
    val recCLILOCID: String?,

    @SerializedName("RecFlgImpreso")
    val recFlgImpreso: String?,

    @SerializedName("RecFlgAnulado")
    val recFlgAnulado: String?,

    @SerializedName("RecFlgSync")
    var recFlgSync: String?,

    @SerializedName("RecMonId")
    val recMonID: String?,

    @SerializedName("RecTipoCambio")
    val recTipoCambio: Double,

    @SerializedName("RecImp")
    val recImp: Double,

    @SerializedName("RecImpBase")
    val recImpBase: Double,

    @SerializedName("RecDepId")
    val recDepID: String?,

    @SerializedName("RecOfcid")
    val recOfcid: String?,

    @SerializedName("RecUsuId")
    val recUsuID: String?,

    @SerializedName("RecPlaNro")
    val recPlaNro: String?,

    @SerializedName("RecRepId")
    val recRepID: String?,

    @SerializedName("RecObservaciones")
    val recObservaciones: String?,

    @SerializedName("RecNumAdenda")
    val recNumAdenda: String?,

    @SerializedName("RecLat")
    val recLat: String?,

    @SerializedName("RecLong")
    val recLong: String?,

    @SerializedName("Valores")
    var valores: MutableList<ReciboValor> = mutableListOf(),

    @SerializedName("Referencias")
    var referencias: List<ReciboReferencia> = mutableListOf()
)