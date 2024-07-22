package com.camera.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "clientes_locales", primaryKeys = ["EmpId", "CliId", "CliLocId"])
data class ClientesLocalesEntity(
    @ColumnInfo(name = "EmpId")
    var empID: Long = 0L,

    @ColumnInfo(name = "CliId")
    var cliID: String = "",

    @ColumnInfo(name = "CliLocId")
    var cliLOCID: String = "",

    @ColumnInfo(name = "CliLocDir")
    var cliLOCDir: String = "",

    @ColumnInfo(name = "CliLocNom")
    var cliLOCNom: String = "",

    @ColumnInfo(name = "CliLocDirNro")
    var cliLOCDirNro: Long = 0L,

    @ColumnInfo(name = "modDT")
    var modDT: String = "",

    @ColumnInfo(name = "CliLocZonaId")
    var cliLOCZonaID: String = "",

    @ColumnInfo(name = "CliLocZonaDsc")
    var cliLOCZonaDsc: String = "",

    @ColumnInfo(name = "CliLocRegionId")
    var cliLOCRegionID: String = "",

    @ColumnInfo(name = "CliLocRegionDsc")
    var cliLOCRegionDsc: String = "",

    @ColumnInfo(name = "CliLocPaisId")
    var cliLOCPaisID: String = "",

    @ColumnInfo(name = "CliLocPaisDsc")
    var cliLOCPaisDsc: String = "",

    @ColumnInfo(name = "CliLocBarrioId")
    var cliLOCBarrioID: String = "",

    @ColumnInfo(name = "CliLocBarrioDsc")
    var cliLOCBarrioDsc: String = "",

    @ColumnInfo(name = "CLiLocLocalidadId")
    var cLiLOCLocalidadID: String = "",

    @ColumnInfo(name = "CliLocalidadDsc")
    var cliLocalidadDsc: String = "",

    @ColumnInfo(name = "CliLocMail")
    var cliLOCMail: String = "",

    @ColumnInfo(name = "CliLoctelefono")
    var cliLoctelefono: String = "",

    @ColumnInfo(name = "CliLocActivo")
    var cliLOCActivo: String = "",

    @ColumnInfo(name = "CliLocLat")
    var cliLOCLat: String = "",

    @ColumnInfo(name = "CliLocLong")
    var cliLOCLong: String = "",

    @ColumnInfo(name = "CliLocRadioAccion")
    var cliLOCRadioAccion: Long = 0L,

    @ColumnInfo(name = "CliLocCelular")
    var cliLOCCelular: String = "",

    @ColumnInfo(name = "CliLocContacto")
    var cliLOCContacto: String = "",

    @ColumnInfo(name = "CliLocLatLongSyncFlag")
    var cliLocLatLongSyncFlag: String = ""
)