package com.camera.data.repository

import android.content.Context
import com.camera.data.api.EnvioImagenesMultiPartApi
import com.camera.data.models.UserProfile
import com.camera.data.models.response.ApiCreationResponse
import com.camera.utils.LogInfo
import com.camera.utils.getPhoneImei
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.File
import javax.inject.Inject

/**
 ** Created by Tomas Maurente on 1/7/2024 5:00 AM
 ** tommaurente@gmail.com
 **/
class EnvioImagenesMultiPartRepository @Inject constructor(
    private val envioImagenesMultiPartApi: EnvioImagenesMultiPartApi,
    @ApplicationContext private val context: Context
) {
    lateinit var response: Response

    suspend fun subirImagen(user: UserProfile, file: File): Boolean {
        var result: Boolean
        try {

            response =
                envioImagenesMultiPartApi.uploadFile(
                    user,
                    getPhoneImei(context) ?: "SINIMEI",
                    file.name,
                    file.asRequestBody("multipart/form-data; ".toMediaType())
                )

            val respuesta = Gson().fromJson(response.body?.string(), ApiCreationResponse::class.java)
            result = response.isSuccessful && respuesta != null && respuesta.errors.isNullOrEmpty() && respuesta.quantity > 0
        } catch (e: Exception) {
            result = false
            LogInfo("MultiPart error sending " + file.name + " Error: " + e.message.toString())
        }
        return result
    }
}
