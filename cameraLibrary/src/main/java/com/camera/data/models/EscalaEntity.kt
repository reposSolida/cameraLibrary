package com.camera.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 ** Created by Carlos A. Novaez Guerrero on 3/22/2023 9:02 AM
 ** cnovaez.dev@outlook.com
 **/

@Entity(tableName = "escalas", primaryKeys = ["EmpId", "PolId", "EscalaId"])
data class EscalaEntity(
    @ColumnInfo(name = "EmpId")
    val empID: Long,

    @ColumnInfo(name = "PolId")
    val polID: String,

    @ColumnInfo(name = "EscalaId")
    val escalaID: String,

    @ColumnInfo(name = "EscalaDsc")
    val escalaDsc: String,

    @ColumnInfo(name = "EscalaAbr")
    val escalaABR: String,

    @ColumnInfo(name = "modDT")
    val modDT: String,

    @ColumnInfo(name = "EscalaActiva")
    val escalaActiva: String,

    @ColumnInfo(name = "EscalaDesde")
    val escalaDesde: Long,

    @ColumnInfo(name = "EscalaHasta")
    val escalaHasta: Long,

    @ColumnInfo(name = "EscalaProdId")
    val escalaProdID: String,

    @ColumnInfo(name = "EscalaCant")
    var escalaCant: Double,

    @ColumnInfo(name = "EscalaCondVtaId")
    val escalaCondVtaID: String,

    @ColumnInfo(name = "EscalaMultipo")
    val escalaMultipo: Double
)