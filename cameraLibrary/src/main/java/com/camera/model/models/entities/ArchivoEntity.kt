package com.camera.model.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.camera.model.models.Archivo
import com.google.gson.annotations.SerializedName

@Entity(tableName = "archivos", primaryKeys = ["EmpId", "ArchivoId"])
data class ArchivoEntity(
    @ColumnInfo(name = "EmpId")
    var empID: Long = 0L,

    @ColumnInfo(name = "ArchivoId")
    var archivoID: String = "",

    @ColumnInfo(name = "ArchDsc")
    var archDsc: String = "",

    @ColumnInfo(name = "ArchFecha")
    var archFecha: String = "",

    @ColumnInfo(name = "ArchGpoCLi")
    var archGpoCLi: String = "",

    @ColumnInfo(name = "ArchCliID")
    var archCLIID: String = "",

    @ColumnInfo(name = "ArchPathFile")
    var archPathFile: String = "",

    @ColumnInfo(name = "ArchURL")
    var archURL: String = "",

    @ColumnInfo(name = "ArchNomFile")
    var archNomFile: String = "",

    @ColumnInfo(name = "ArchActivo")
    var archActivo: String = "",

    @ColumnInfo(name = "ArchFlgSync")
    var archFlgSync: String = "",

    @ColumnInfo(name = "modDT")
    var modDT: String = "",

    @ColumnInfo(name = "ArchCliLocId")
    var archCLILOCID: String = "",

    @ColumnInfo(name = "ArchTipo")
    var archTipo: String = "",

    @ColumnInfo(name = "ArchCategoriaID")
    var archCategoriaID: String = "",

    @ColumnInfo(name = "ArchObservaciones")
    var archObservaciones: String = "",

    @ColumnInfo(name = "ArchUsuId")
    var archUsuId: String = "",

    @ColumnInfo(name = "VenId")
    var venId: String = "",

    @ColumnInfo(name = "CliId")
    var cliId: String = "",

    @SerializedName("ArchLat")
    val archLat: String = "" ,

    @SerializedName("ArchLong")
    val archLong: String = ""   ,
) {
    fun toApi() = Archivo(
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
        modDT,
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