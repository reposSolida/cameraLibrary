package com.camera.data.models

import com.google.gson.annotations.SerializedName

data class ReciboValor(
    @SerializedName("EmpId") var empId: Long = 0,
    @SerializedName("RecTpoDocId") var recTpoDocId: String = "",
    @SerializedName("RecDocId") var recDocId: String = "",
    @SerializedName("RecValLin") var recValLin: Int = 0,
    @SerializedName("RecValMedPagoId") var recValMedPagoId: String = "",
    @SerializedName("RecValMonId") var recValMonId: String = "",
    @SerializedName("RecValIMp") var recValIMp: Double = 0.0,
    @SerializedName("RecValImpBase") var recValImpBase: Double = 0.0,
    @SerializedName("BancoId") var bancoId: String = "",
    @SerializedName("BancoSucId") var bancoSucId: String = "",
    @SerializedName("RecValNroDoc") var recValNroDoc: String = "",
    @SerializedName("RecValCtaBanco") var recValCtaBanco: String = "",
    @SerializedName("RecValRut") var recValRut: String = "",
    @SerializedName("RecValObs") var recValObs: String = "",
    @SerializedName("RecValFchEmi") var recValFchEmi: String = "",
    @SerializedName("RecValFchVto") var recValFchVto: String = "",
    @SerializedName("RecValModDt") var recValModDt: String = "",
)