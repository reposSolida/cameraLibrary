package com.camera.data.models

import com.camera.data.models.entities.PhotoEntity
import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("EmpId")
    var empID: Long = 0L,

    @SerializedName("FotoID")
    var fotoID: String = "",

    @SerializedName("FotoCliiD")
    var fotoCliiD: String = "",

    @SerializedName("FotoCliLocId")
    var fotoCLILOCID: String = "",

    @SerializedName("FotoFecha")
    var fotoFecha: String = "",

    @SerializedName("FotoDsc")
    var fotoDsc: String = "",

    @SerializedName("FotoPathFile")
    var fotoPathFile: String = "",

    @SerializedName("FotoNombre")
    var fotoNombre: String = "",

    @SerializedName("FotoCategoria")
    var fotoCategoria: String = "",

    @SerializedName("FotoGrupo")
    var fotoGrupo: String = "",

    @SerializedName("FotoActiva")
    var fotoActiva: String = "",

    @SerializedName("FotoModdt")
    var fotoModdt: String = "",

    @SerializedName("FotoFlgSync")
    var fotoFlgSync: String = "",

    @SerializedName("FotoBlob")
    var fotoBlob: ByteArray,

    @SerializedName("FotoUsuid")
    var fotoUsuid: String = "",

    @SerializedName("FotoVenId")
    var fotoVenID: String = "",

    @SerializedName("FotoObs")
    var fotoObs: String = "",

    @SerializedName("FotoLat")
    var fotoLat: String = "",

    @SerializedName("FotoLong")
    var fotoLong: String = "",
) {
    fun toEntity() = PhotoEntity(
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
        fotoBlob,
        fotoUsuid,
        fotoVenID,
        fotoObs,
        fotoLat,
        fotoLong
    )
}