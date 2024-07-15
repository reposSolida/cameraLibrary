package com.camera.model.api

import android.util.Log
import com.camera.model.models.UserProfile
import com.camera.model.utils.GlobalValues
import com.camera.model.utils.PlatformTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import javax.inject.Inject

/**
 ** Created by Tomas Maurente on 26/6/2024 17:13
 **/

class EnvioImagenesMultiPartApi @Inject constructor() {

    suspend fun uploadFile(
        user: UserProfile,
        imei: String,
        photoId: String,
        body: RequestBody
    ): Response {

        Log.d("o", "some message")

        val client = OkHttpClient()
        val request = Request.Builder()

            .url(user.environment + "sd.aws_set_imagenes_v1")
            .post(body)
            .addHeader("UsuId", user.username)
            .addHeader("EmpId", user.empId)
            .addHeader("Pass", user.pass)
            .addHeader("DeviceId", GlobalValues.defaultDeviceId)
            .addHeader("Imei", imei)
            .addHeader("Token", GlobalValues.defaultToken)
            .addHeader("Plataforma", PlatformTypes.Mobile.toString())
            .addHeader("Content-Type", "multipart/form-data; ")
            .addHeader("Id1", photoId)
            .build()

        return withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
    }
}
