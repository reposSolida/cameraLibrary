package com.camera.data.models

import com.google.gson.annotations.SerializedName

data class DocumentPhotos(
    @SerializedName("EmpId")
    val empID: Int,

    @SerializedName("TpoDocId")
    val tpoDocID: String,

    @SerializedName("DocUsuId")
    val docUsuID: String,

    @SerializedName("DocId")
    val docID: String,

    @SerializedName("Archivo")
    val archivo: List<Archivos>,
)

data class Archivos(
    @SerializedName("ArchivoId")
    val archivoID: String
)


