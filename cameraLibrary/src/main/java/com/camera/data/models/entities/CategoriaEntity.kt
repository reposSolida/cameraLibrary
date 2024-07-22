package com.camera.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "categorias", primaryKeys = ["EmpId", "CategoriaId"])
data class CategoriaEntity(
    @ColumnInfo(name = "EmpId")
    val empID: Long,

    @ColumnInfo(name = "CategoriaId")
    val categoriaID: String,

    @ColumnInfo(name = "CategoriaDsc")
    val categoriaDsc:  String?,

    @ColumnInfo(name = "modDT")
    val categoriaModDT:  String?,

    @ColumnInfo(name = "CategoriaActiva")
    val categoriaActiva:  String?
)