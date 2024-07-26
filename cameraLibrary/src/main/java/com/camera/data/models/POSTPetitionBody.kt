package com.camera.data.models

import com.camera.utils.GlobalValues
import com.camera.utils.PlatformTypes
import retrofit2.http.Field
import retrofit2.http.Header

data class POSTPetitionBody(
    @Header("UsuId") var UsuId: String,
    @Header("Pass") var Pass: String,
    @Header("Imei") var Imei: String,
    @Header("EmpId") var EmpId: String = "0",
    @Header("DeviceId") var DeviceId: String = GlobalValues.defaultDeviceId,
    @Header("Token") var Token: String = GlobalValues.defaultToken,
    @Header("Plataforma") var Plataforma: String = PlatformTypes.Mobile.toString(),
    @Header("ModuloMobile") var ModuloMobile: String = GlobalValues.moduleMobile,
    @Header("Id1") var Id1: String = "",
    @Header("Id2") var Id2: String = "",
    @Header("Id3") var Id3: String = "",
    @Header("Id4") var Id4: String = "",
    @Header("Id5") var Id5: String = "",
    @Header("DtUsuario") var DtUsuario: String = "",
    @Header("DtOper") var DtOper : String = "",
    @Header("Content-type") var ContentType: String = "",
    @Field("CliId") var CliId: String = "",
    @Field("jsonBodyIn") var jsonBodyIn: String = "",
) {
    fun toMap(): Map<String, String> {
        val map = HashMap<String, String>()
        map["UsuId"] = UsuId
        map["Pass"] = Pass
        map["EmpId"] = EmpId
        map["Imei"] = Imei
        map["DeviceId"] = DeviceId
        map["Token"] = Token
        map["Plataforma"] = Plataforma
        map["ModuloMobile"] = ModuloMobile
        if (Id1.isNotEmpty()) map["Id1"] = Id1
        if (Id2.isNotEmpty()) map["Id2"] = Id2
        if (Id3.isNotEmpty()) map["Id3"] = Id3
        if (Id4.isNotEmpty()) map["Id4"] = Id4
        if (Id5.isNotEmpty()) map["Id5"] = Id5
        if(ContentType.isNotEmpty()) map["Content-type"] = ContentType
        if (DtUsuario.isNotEmpty()) map["DtUsuario"] = DtUsuario
        if (DtOper.isNotEmpty()) map["DtOper"] = DtOper
        return map
    }
}

