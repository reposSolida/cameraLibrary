package com.camera.data.repository

import android.content.Context
import com.camera.data.models.dao.PhotosDao
import com.camera.data.api.PhotosApi
import com.camera.data.models.Photo
import com.camera.data.models.UserProfile
import com.camera.data.models.entities.PhotoEntity
import com.camera.data.models.response.ApiCreationResponse
import com.camera.data.models.response.PhotosResponse
import com.camera.data.models.response.WsRespuesta
import com.camera.domain.useCases.GetTableModDTUseCase
import com.camera.utils.SyncFlgStateType
import com.camera.utils.Utils
import com.camera.utils.LogError
import com.camera.utils.LogInfo
import com.camera.utils.getGeneralSP
import com.camera.utils.getPhoneImei
import com.camera.utils.isDeviceIsConnectedToTheInternet
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject


class PhotosRepository @Inject constructor(
    private val photosApi: PhotosApi,
    private val photosDao: PhotosDao,
    private val getTableModDTUseCase: GetTableModDTUseCase,
    @ApplicationContext private val context: Context
) {

    suspend fun getAllPhotos(
        user: UserProfile
    ): WsRespuesta? {
        var wsResponse: WsRespuesta = WsRespuesta()

        try {

            if (context.isDeviceIsConnectedToTheInternet()) {
                LogInfo("calling getAllPhotos  with user: ${user.username}  and company: ${user.empId} ")

                val res: Response<PhotosResponse> =
                    photosApi.getPhotos(
                        com.camera.data.models.POSTPetitionBody(
                            user.username,
                            user.pass,
                            getPhoneImei(context) ?: "SINIMEI",
                            user.empId,
                            DtOper = if (getGeneralSP(context).modDT) getTableModDTUseCase.invoke(
                                user.empId.toInt(),
                                "photos"
                            ) else ""
                        ).toMap()
                    )

                val response = res.body()
                val photos = response?.photos
                val resWs = response?.sdtWSRespuesta
                if (resWs != null) {
                    wsResponse = resWs
                }
                if (photos == null) {
                    LogInfo("No Photos associated to User: $user")
                    if (!wsResponse.errdsc.isNullOrEmpty()) {
                        LogError("Error code: ${wsResponse?.errcod}, Error description: ${wsResponse?.errdsc}")
                    }
                } else {

                    LogInfo("Inserting list of ${photos.size} Photos in the db")
                    storePhotosInDB(
                        photos.map { it.toEntity() }
                    )
                    LogInfo("Photos stored in database")
                }
            }


        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllPhotos", ex)
        }

        return wsResponse
    }

    suspend fun insertPhotosApiAndDB(
        user: UserProfile,
        photos: List<Photo>,
        storeInDb: Boolean = true
    ): WsRespuesta? {
        var wsResponse: WsRespuesta = WsRespuesta()

        try {

            if (context.isDeviceIsConnectedToTheInternet()) {
                LogInfo("calling insertPhotosApiAndDB  with user: ${user.username}  and company: ${user.empId} ")

                val res: Response<ApiCreationResponse> =
                    photosApi.sendPhotos(
                        com.camera.data.models.POSTPetitionBody(
                            user.username,
                            user.pass,
                            getPhoneImei(context) ?: "SINIMEI",
                            user.empId
                        ).toMap(),
                        photos
                    )

                val response = res.body()
                val errors = response?.errors
                val resWs = response?.sdtWSRespuesta
                if (resWs != null) {
                    wsResponse = resWs
                }
                if (errors.isNullOrEmpty() && response != null && response.quantity > 0) {
                    LogInfo("Photos created correctly")
                    //traza = trazaIsSynchronize(traza)
                    photos.forEach { photo ->
                        photo.fotoFlgSync = SyncFlgStateType.Sincronizado.toString()
                        photo.fotoModdt = Utils.getCurrentDateAndTime()
                    }
                    storePhotosInDB(photos.map { it.toEntity() })
                    LogInfo("Photos stored in db correctly")
                } else {
                    if (!wsResponse.errdsc.isNullOrEmpty()) {
                        LogError("Error code: ${wsResponse.errcod}, Error description: ${wsResponse.errdsc}")
                    }
                    LogInfo("Storing Photo in db without sync due to an error in the creation in the ws")
                    photos.forEach { photo ->
                        photo.fotoFlgSync = SyncFlgStateType.Error.toString()
                        photo.fotoModdt = Utils.getCurrentDateAndTime()
                    }
                    storePhotosInDB(photos.map { it.toEntity() })
                    LogInfo("Finished storing Photo in db without sync due to an error in the creation in the ws")
                }

            } else {
                if (storeInDb) {
                    LogInfo("Storing Photo db without sync due to device not having internet")
                    storePhotosInDB(photos.map { it.toEntity() })
                    LogInfo("Photo stored in db correctly")
                }
            }


        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in insertPhotosApiAndDB", ex)
        }

        return wsResponse
    }

    suspend fun getAllPhotosSyncPendingFiles(): List<PhotoEntity> {
        return try {
            photosDao.getPhotosPendingToSync()
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllPhotosSyncPendingFiles", ex)
            emptyList()
        }
    }

    //get all photos by id
    suspend fun getAllPhotosById(ids: List<String>): List<PhotoEntity> {
        return try {
            photosDao.getAllPhotosById(ids)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllPhotosById", ex)
            emptyList()
        }
    }

    suspend fun storePhotosInDB(
        data: List<PhotoEntity>
    ) {
        LogInfo("Inserting ${data.size} photos in database")
        try {
            photosDao.insert(data)
            LogInfo("Photos inserted correctly")
        } catch (ex: Exception) {
            LogError("Error in storePhotosInDB: ", ex)
        }

    }


    suspend fun deletePhotoById(empId: Int, photoId: String) {
        try {
            photosDao.deletePhotoById(empId, photoId)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in deletePhotoById", ex)
        }
    }
}