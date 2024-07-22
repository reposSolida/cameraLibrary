package com.camera.data.models

import com.camera.data.models.entities.CategoriaEntity
import com.google.gson.annotations.SerializedName

data class Categoria(
    @SerializedName("EmpId")
    val empID: Long,

    @SerializedName("CategoriaId")
    val categoriaID: String,

    @SerializedName("CategoriaDsc")
    val categoriaDsc: String,

    @SerializedName("CategoriaModDT")
    val categoriaModDT: String,

    @SerializedName("CategoriaActiva")
    val categoriaActiva: String
) {
    fun toEntity() =
        CategoriaEntity(empID, categoriaID, categoriaDsc, categoriaModDT, categoriaActiva)
}