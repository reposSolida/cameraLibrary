package com.camera.data.models

import com.camera.data.models.entities.ArchivoEntity
import com.google.gson.annotations.SerializedName

data class Archivo(
    @SerializedName("EmpId")
    val empID: Long,

    @SerializedName("ArchivoId")
    val archivoID: String,

    @SerializedName("ArchDsc")
    val archDsc: String,

    @SerializedName("ArchFecha")
    val archFecha: String,

    @SerializedName("ArchGpoCLi")
    val archGpoCLi: String,

    @SerializedName("ArchCliID")
    val archCLIID: String,

    @SerializedName("ArchPathFile")
    val archPathFile: String,

    @SerializedName("ArchURL")
    val archURL: String,

    @SerializedName("ArchNomFile")
    val archNomFile: String,

    @SerializedName("ArchActivo")
    val archActivo: String,

    @SerializedName("ArchFlgSync")
    var archFlgSync: String,

    @SerializedName("ArchModDt")
    var archModDt: String,

    @SerializedName("ArchCliLocId")
    val archCLILOCID: String,

    @SerializedName("ArchTipo")
    val archTipo: String,

    @SerializedName("ArchCategoriaID")
    val archCategoriaID: String,

    @SerializedName("ArchObservaciones")
    val archObservaciones: String,

    @SerializedName("ArchUsuId")
    val archUsuId: String,

    @SerializedName("VenId")
    val venId: String  ,

    @SerializedName("CliId")
    val cliId: String ,

    @SerializedName("ArchLat")
    val archLat: String ,

    @SerializedName("ArchLong")
    val archLong: String   ,
) {
    fun toEntity() = ArchivoEntity(
        empID,
        archivoID,
        archDsc,
        archFecha,
        archGpoCLi,
        archCLIID,
        archPathFile,
        archURL,
        archNomFile,
        archActivo,
        archFlgSync,
        archModDt,
        archCLILOCID,
        archTipo,
        archCategoriaID,
        archObservaciones,
        archUsuId,
        venId,
        cliId,
        archLat,
        archLong
    )
}