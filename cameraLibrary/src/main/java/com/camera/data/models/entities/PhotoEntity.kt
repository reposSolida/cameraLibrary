package com.camera.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.camera.data.models.Photo

@Entity(tableName = "photos", primaryKeys = ["EmpId", "FotoID"])
data class PhotoEntity(
    @ColumnInfo(name = "EmpId")
    var empID: Long = 0L,

    @ColumnInfo(name = "FotoID")
    var fotoID: String = "",

    @ColumnInfo(name = "FotoCliiD")
    var fotoCliiD: String = "",

    @ColumnInfo(name = "FotoCliLocId")
    var fotoCLILOCID: String = "",

    @ColumnInfo(name = "FotoFecha")
    var fotoFecha: String = "",

    @ColumnInfo(name = "FotoDsc")
    var fotoDsc: String = "",

    @ColumnInfo(name = "FotoPathFile")
    var fotoPathFile: String = "",

    @ColumnInfo(name = "FotoNombre")
    var fotoNombre: String = "",

    @ColumnInfo(name = "FotoCategoria")
    var fotoCategoria: String = "",

    @ColumnInfo(name = "FotoGrupo")
    var fotoGrupo: String = "",

    @ColumnInfo(name = "FotoActiva")
    var fotoActiva: String = "",

    @ColumnInfo(name = "modDT")
    var fotoModdt: String = "",

    @ColumnInfo(name = "FotoFlgSync")
    var fotoFlgSync: String = "",

    @ColumnInfo(name = "FotoBlob")
    var fotoBlob: ByteArray? = null,

    @ColumnInfo(name = "FotoUsuid")
    var fotoUsuid: String = "",

    @ColumnInfo(name = "FotoVenId")
    var fotoVenID: String = "",

    @ColumnInfo(name = "FotoObs")
    var fotoObs: String = "",

    @ColumnInfo(name = "FotoLat")
    var fotoLat: String = "",

    @ColumnInfo(name = "FotoLong")
    var fotoLong: String = "",
) {
    fun toApi() = Photo(
        empID,
        fotoID,
        fotoCliiD,
        fotoCLILOCID,
        fotoFecha,
        fotoDsc,
        fotoPathFile,
        fotoNombre,
        fotoCategoria,
        fotoGrupo,
        fotoActiva,
        fotoModdt,
        fotoFlgSync,
        fotoBlob!!,
        fotoUsuid,
        fotoVenID,
        fotoObs,
        fotoLat,
        fotoLong
    )
}