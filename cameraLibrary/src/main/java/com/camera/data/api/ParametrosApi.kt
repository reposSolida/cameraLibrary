package com.camera.data.api

import com.camera.data.models.response.ParametrosResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ParametrosApi {

    @FormUrlEncoded
    @POST("sd.aws_get_parametros_v1")
    suspend fun getParametros(@FieldMap fields: Map<String, String>): Response<ParametrosResponse>
}