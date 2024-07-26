package com.camera.data.models

import com.google.gson.annotations.SerializedName

data class ReciboReferencia(
    @SerializedName("EmpId")
    var empId: Long = 0L,
    @SerializedName("RecTpoDocId")
    var recTpoDocId: String = "",
    @SerializedName("RecDocId")
    var recDocId: String = "",
    @SerializedName("RecRefLin")
    var recRefLinvar: Int = 0,
    @SerializedName("TpoDocId")
    var tpoDocId: String = "",
    @SerializedName("DocUsuId")
    var docUsuId: String = "",
    @SerializedName("DocId")
    var docId: String = "",
    @SerializedName("RecRefMonId")
    var recRefMonId: String = "",
    @SerializedName("RecRefImp")
    var recRefImp: Double = 0.0,
    @SerializedName("RecRefImpBase")
    var recRefImpBase: Double = 0.0,
    @SerializedName("RecDocNro")
    var recDocNro: String = "",
    @SerializedName("RecRefCuota")
    var recRefCuota: String = "",
)