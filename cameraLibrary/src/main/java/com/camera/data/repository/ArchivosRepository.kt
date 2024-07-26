package com.camera.data.repository

import android.content.Context
import com.camera.data.models.dao.ArchivosDao
import com.camera.data.api.ArchivosApi
import com.camera.data.models.Archivo
import com.camera.data.models.POSTPetitionBody
import com.camera.data.models.UserProfile
import com.camera.data.models.entities.ArchivoEntity
import com.camera.data.models.response.ApiCreationResponse
import com.camera.data.models.response.WsRespuesta
import com.camera.utils.SyncFlgStateType
import com.camera.utils.Utils
import com.camera.utils.LogError
import com.camera.utils.LogInfo
import com.camera.utils.getPhoneImei
import com.camera.utils.isDeviceIsConnectedToTheInternet
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class ArchivosRepository @Inject constructor(
    private val archivosApi: ArchivosApi,
    private val archivosDao: ArchivosDao,
    @ApplicationContext private val context: Context
) {

    suspend fun insertArchivosListApiAndDB(
        user: UserProfile,
        archivos: List<Archivo>,
        storeInDb: Boolean = true
    ): WsRespuesta? {
        var wsResponse: WsRespuesta = WsRespuesta()

        try {

            if (context.isDeviceIsConnectedToTheInternet()) {
                LogInfo("calling insertArchivosApiAndDB  with user: ${user.username}  and company: ${user.empId} ")

                val res: Response<ApiCreationResponse> =
                    archivosApi.createArchivos(
                        POSTPetitionBody(
                            user.username,
                            user.pass,
                            getPhoneImei(context) ?: "SINIMEI",
                            user.empId
                        ).toMap(),
                        archivos
                    )

                val response = res.body()
                val errors = response?.errors
                val resWs = response?.sdtWSRespuesta
                if (resWs != null) {
                    wsResponse = resWs
                }
                if (errors.isNullOrEmpty() && response != null && response.quantity > 0) {
                    LogInfo("Archives created correctly")
                    //traza = trazaIsSynchronize(traza)
                    if (archivos.isNotEmpty()) {
                        archivos.forEach {
                            it.archFlgSync = SyncFlgStateType.Sincronizado.toString()
                            it.archModDt = Utils.getCurrentDateAndTime()
                        }
                        storeArchivosInDB(archivos.map { it.toEntity() })
                        LogInfo("Archives stored in db correctly")
                    } else {
                        LogInfo("Archives is empty, no sync flag could be updated")
                    }
                } else {
                    if (wsResponse.errdsc.isNotEmpty()) {
                        LogError("Error code: ${wsResponse.errcod}, Error description: ${wsResponse.errdsc}")
                    }
                    LogInfo("Storing Archive in db without sync due to an error in the creation in the ws")
                    archivos.forEach {
                        it.archFlgSync = SyncFlgStateType.Error.toString()
                        it.archModDt = Utils.getCurrentDateAndTime()
                    }

                    storeArchivosInDB(archivos.map { it.toEntity() })
                    LogInfo("Finished storing Archive in db without sync due to an error in the creation in the ws")
                }
            } else {
                if (storeInDb) {
                    LogInfo("Storing Archive db without sync due to device not having internet")
                    storeArchivosInDB(archivos.map { it.toEntity() })
                    LogInfo("Archive stored in db correctly")
                }
            }


        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in insertArchivosListApiAndDB", ex)
        }

        return wsResponse
    }

    suspend fun storeArchivosInDB(
        data: List<ArchivoEntity>
    ) {
        try {
                archivosDao.insert(data.also {
                    it.forEach { archivo ->
                        archivo.archTipo = archivo.archTipo.trim()
                    }
                })
        } catch (ex: Exception) {
            LogError("Error in storearchivosInDB: ", ex)
        }
    }

    suspend fun insertArchivosApiAndDB(
        user: UserProfile,
        archivos: Archivo,
        storeInDb: Boolean = true
    ): WsRespuesta? {
        var wsResponse = WsRespuesta()

        try {

            if (context.isDeviceIsConnectedToTheInternet()) {
                LogInfo("calling insertArchivosApiAndDB  with user: ${user.username}  and company: ${user.empId} ")

                val res: Response<ApiCreationResponse> =
                    archivosApi.createArchivos(
                        POSTPetitionBody(
                            user.username,
                            user.pass,
                            getPhoneImei(context) ?: "SINIMEI",
                            user.empId
                        ).toMap(),
                        listOf(archivos)
                    )

                val response = res.body()
                val errors = response?.errors
                val resWs = response?.sdtWSRespuesta
                if (resWs != null) {
                    wsResponse = resWs
                }
                if (errors.isNullOrEmpty() && response != null && response.quantity > 0) {
                    LogInfo("Archivos created correctly")
                    //traza = trazaIsSynchronize(traza)
                    archivos.archFlgSync = SyncFlgStateType.Sincronizado.toString()
                    archivos.archModDt = Utils.getCurrentDateAndTime()
                    storeArchivosInDB(listOf(archivos.toEntity()))
                    LogInfo("Archives stored in db correctly")
                } else {
                    if (!wsResponse.errdsc.isNullOrEmpty()) {
                        LogError("Error code: ${wsResponse.errcod}, Error description: ${wsResponse.errdsc}")
                    }
                    LogInfo("Storing Archive in db without sync due to an error in the creation in the ws")
                    archivos.archFlgSync = SyncFlgStateType.Error.toString()
                    archivos.archModDt = Utils.getCurrentDateAndTime()
//                    archivos. =
//                        "Error code: ${wsResponse.errcod}, Error description: ${wsResponse.errdsc}"
                    //   documento.docSyncIntentos = documento.docSyncIntentos + 1
                    storeArchivosInDB(listOf(archivos.toEntity()))
                    LogInfo("Finished storing Archive in db without sync due to an error in the creation in the ws")
                }

            } else {
                if (storeInDb) {
                    LogInfo("Storing Archive db without sync due to device not having internet")
                    storeArchivosInDB(listOf(archivos.toEntity()))
                    LogInfo("Archive stored in db correctly")
                }
            }


        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in insertArchivosApiAndDB", ex)
        }

        return wsResponse
    }

    suspend fun getAllSyncPendingFiles(): List<ArchivoEntity> {
        return try {
            archivosDao.getArchivosPendingToSync()
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllSyncPendingFiles", ex)
            emptyList()
        }
    }
}