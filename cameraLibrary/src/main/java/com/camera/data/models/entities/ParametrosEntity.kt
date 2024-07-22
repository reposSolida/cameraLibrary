package com.camera.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "parametros", primaryKeys = ["EmpId", "Parid"])
data class ParametrosEntity(
    @ColumnInfo(name = "EmpId")
    val empID: Long,
    @ColumnInfo(name = "Parid")
    val parid: String,

    @ColumnInfo(name = "ParDsc")
    val parDsc: String?,

    @ColumnInfo(name = "ParNum")
    val parNum: Long?,

    @ColumnInfo(name = "ParDate")
    val parDate: String?,

    @ColumnInfo(name = "ParDateTime")
    val parDateTime: String?,

    @ColumnInfo(name = "ParTxt")
    var parTxt: String?,

    @ColumnInfo(name = "ParTipo")
    val parTipo: String?,

    @ColumnInfo(name = "modDT")
    val parModDT: String?,

    @ColumnInfo(name = "GrupoParmId")
    val grupoParmID: String?
)

