package com.camera.data.api

import com.camera.data.models.Archivo
import com.camera.data.models.response.ApiCreationResponse
import com.camera.data.models.response.ArchivosResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ArchivosApi {

    @FormUrlEncoded
    @POST("sd.aws_get_archivos_v3")
    suspend fun getArchivos(@FieldMap fields: Map<String, String>): Response<ArchivosResponse>

    @POST("sd.aws_set_archivos_v2")
    suspend fun createArchivos(
        @HeaderMap fields: Map<String, String>,
        @Body archivo: List<Archivo>
    ): Response<ApiCreationResponse>
}