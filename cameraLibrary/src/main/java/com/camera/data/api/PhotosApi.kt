package com.camera.data.api

import com.camera.data.models.Photo
import com.camera.data.models.response.ApiCreationResponse
import com.camera.data.models.response.PhotosResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface PhotosApi {

    @POST("sd.aws_get_fotos_v1")
    suspend fun getPhotos(@HeaderMap fields: Map<String, String>): Response<PhotosResponse>

    @POST("sd.aws_set_fotos_v1")
    suspend fun sendPhotos(
        @HeaderMap fields: Map<String, String>,
        @Body photos: List<Photo>
    ): Response<ApiCreationResponse>
}