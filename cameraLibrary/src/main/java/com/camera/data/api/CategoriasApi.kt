package com.camera.data.api

import com.camera.data.models.response.CategoriasResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CategoriasApi {

    @FormUrlEncoded
    @POST("sd.aws_get_categorias_v1")
    suspend fun getCategorias(@FieldMap fields: Map<String, String>): Response<CategoriasResponse>
}