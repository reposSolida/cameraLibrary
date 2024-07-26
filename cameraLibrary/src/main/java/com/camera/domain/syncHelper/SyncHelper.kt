package com.camera.domain.syncHelper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.camera.data.models.Archivo
import com.camera.data.models.UserProfile
import com.camera.data.models.UserSessionData.user
import com.camera.data.models.entities.ArchivoEntity
import com.camera.data.models.entities.PhotoEntity
import com.camera.data.models.response.WsRespuesta
import com.camera.domain.useCases.DeletePhotoByIdUseCase
import com.camera.domain.useCases.GetAllCategoriasDBUseCase
import com.camera.domain.useCases.GetAllPhotosSyncPendingFilesDBUseCase
import com.camera.domain.useCases.GetAllSyncPendingFilesDBUseCase
import com.camera.domain.useCases.GetParametroByParIdUseCase
import com.camera.domain.useCases.InsertArchivosApiAndDBUseCase
import com.camera.domain.useCases.InsertArchivosListApiAndDBUseCase
import com.camera.domain.useCases.InsertWithMultiPartUseCase
import com.camera.domain.useCases.StorePhotosInDBUseCase
import com.camera.utils.AnimExtras
import com.camera.utils.CustomToast
import com.camera.utils.DataReturnType
import com.camera.utils.GeneralStateType
import com.camera.utils.LogError
import com.camera.utils.LogInfo
import com.camera.utils.MessageTypeIcon
import com.camera.utils.OnSyncFinishedListener
import com.camera.utils.SyncFlgStateType
import com.camera.utils.Utils
import com.camera.utils.customInformationMsgWithAnimation
import com.camera.utils.customToast
import com.camera.utils.isDeviceIsConnectedToTheInternet
import com.mylibrary.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class SyncHelper @Inject constructor (
    private val getAllPhotosSyncPendingFilesDBUseCase: GetAllPhotosSyncPendingFilesDBUseCase,
    private val insertArchivosListApiAndDBUseCase: InsertArchivosListApiAndDBUseCase,
    private val storePhotosInDBUseCase: StorePhotosInDBUseCase,
    private val insertWithMultiPartUseCase: InsertWithMultiPartUseCase,
    private val getAllSyncPendingFilesDBUseCase: GetAllSyncPendingFilesDBUseCase,
    private val deletePhotoByIdUseCase: DeletePhotoByIdUseCase,
    private val insertArchivosApiAndDBUseCase: InsertArchivosApiAndDBUseCase,
    @ApplicationContext private val context: Context
){
/*
    @Inject
    private lateinit var getAllCategoriasDBUseCase: GetAllCategoriasDBUseCase
    @Inject
    private lateinit var getParametroByParIdUseCase: GetParametroByParIdUseCase
    @Inject
    private lateinit var getAllPhotosSyncPendingFilesDBUseCase: GetAllPhotosSyncPendingFilesDBUseCase
    @Inject
    private lateinit var insertArchivosListApiAndDBUseCase: InsertArchivosListApiAndDBUseCase
    @Inject
    private lateinit var storePhotosInDBUseCase: StorePhotosInDBUseCase
    @Inject
    private lateinit var insertWithMultiPartUseCase: InsertWithMultiPartUseCase
    @Inject
    private lateinit var deletePhotoByIdUseCase: DeletePhotoByIdUseCase
    @Inject
    private lateinit var getAllSyncPendingFilesDBUseCase: GetAllSyncPendingFilesDBUseCase
    @Inject
    private lateinit var insertArchivosApiAndDBUseCase: InsertArchivosApiAndDBUseCase
    @ApplicationContext
    private lateinit var context: Context
*/

    suspend fun syncPendingData(
        user: UserProfile,
        c: Context,
        showNoInternetMsg: Boolean = true,
        listener: OnSyncFinishedListener? = null,
        returnedData: DataReturnType? = null,
        indenpendantCalls: Boolean = false,
    ): Boolean {
        var syncedSuccessFull = false
        try {
            if (context.isDeviceIsConnectedToTheInternet()) {
                LogInfo("Sync all pending info")
                syncPendingPhotoData(user, c)
                syncPendingArchives(indenpendantCalls, user)
                syncedSuccessFull = true
                LogInfo("Finish sennding all pending data")
                if (listener != null) {
                    LogInfo("Starting listener for onSyncFinished return")
                    listener.syncFinishedReturn(returnedData)
                }
            } else {
                withContext(Dispatchers.Main) {
                    if (showNoInternetMsg) context.customInformationMsgWithAnimation(
                        ContextCompat.getString(context, R.string.no_internet_connection),
                        R.raw.anim_no_internet_kitty
                    )
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in syncPendingData", ex)
        }
        return syncedSuccessFull
    }

    private suspend fun syncPendingArchives(indenpendantCalls: Boolean, user: UserProfile) {
        val archs =  getAllSyncPendingFilesDBUseCase.invoke()
        if (archs.isNotEmpty()) {
            if (indenpendantCalls) {
                for (a in archs) {
                    insertArchiveEntireLifeCycle(
                        user = user,
                        archivo = a.toApi(),
                        storeInDB =  false
                    )
                }
            } else {
                insertArchivesListEntireLifeCycle(archs.map { it.toApi() }, false, user)
            }
        }
    }

    /**
     * Call the whole lifecycle for the creation of a archive.
     * Steps: 1 - Attempt to create in ws
     *        2 - If success store in database with the document flag marked as sync
     *        3 - If fail store in database with the document flag marked as pending'
     */
    private suspend fun insertArchiveEntireLifeCycle(user: UserProfile, archivo: Archivo, storeInDB: Boolean
    ): WsRespuesta? {
        return try {
            insertArchivosApiAndDBUseCase.invoke(user, archivo, storeInDB)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in insertArchiveEntireLifeCycle", ex)
            null
        }
    }

    private suspend fun syncPendingPhotoData(
        user: UserProfile,
        c: Context
    ) {
        try {
            val pendingsPairs: MutableList<Pair<PhotoEntity, Bitmap>> = mutableListOf()
            val archivos: MutableList<ArchivoEntity> = mutableListOf()

            val photos = getAllPhotosSyncPendingFilesDBUseCase.invoke()

            if (photos.isNotEmpty()) {
                photos.forEach { photo ->
                    photo.fotoBlob?.let {
                        val bitmap = BitmapFactory.decodeByteArray(
                            photo.fotoBlob, 0, photo.fotoBlob?.size ?: 0
                        )
                        pendingsPairs.add(Pair(photo, bitmap))
                    }
                }

                var qty = 1
                val total = pendingsPairs.size
                var success = false

                for (pair in pendingsPairs) {
                    withContext(Dispatchers.Main) {
                        AnimExtras.toggleAnimGeneric(
                            true,
                            ContextCompat.getString(context, R.string.sending_pending_photos_to_server_label),
                            R.raw.anim_sending_photos,
                            title = "Enviando foto $qty de $total, por favor espere...",
                            context = c
                        )
                        qty += 1
                    }
                    success = storePhotoRelatedData(pair, archivos, user)
                }

                LogInfo("Qty of archivos to store: ${archivos.size}")
                var wsRespuesta: WsRespuesta? = null
                if (archivos.isNotEmpty()) {
                    wsRespuesta = insertArchivesListEntireLifeCycle(archivos.map { it.toApi() }, true, user)
                }
                withContext(Dispatchers.Main) {
                    AnimExtras.dismissData()
                    if (wsRespuesta != null && wsRespuesta.ok == GeneralStateType.S.toString() && success) {
                        c.customToast(
                            CustomToast(
                                ContextCompat.getString(c, R.string.pics_sended_label), duration = Toast.LENGTH_LONG
                            )
                        )
                    } else {
                        c.customToast(
                            CustomToast(
                                ContextCompat.getString(context, R.string.was_not_possible_to_send_pics_label),
                                duration = Toast.LENGTH_LONG,
                                picId = MessageTypeIcon.ERROR.IconType(),
                                titleToast = MessageTypeIcon.ERROR.toString()
                            )
                        )
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in storePendingData", ex)
        }
    }

    private suspend fun storePhotoRelatedData(
        pair: Pair<PhotoEntity, Bitmap>,
        archivos: MutableList<ArchivoEntity>,
        user: UserProfile
    ): Boolean {
        var success = false
        try {
            LogInfo("Attempting to storePhotoRelatedData", true)

            // Creo el archivo en el que almacenare la imagen en formato jpeg
            val res = Utils.enviarFoto(pair)

            // Convierto la imagen de bitmap a jpeg y la almaceno en el archivo creado
            Utils.saveFileOnly(pair.second, res.second)

            val archivo = Utils.generarRegistroArchivo(
                SyncFlgStateType.Pendiente.toString(),
                res.first,
                pair.first,
                user
            )
            if (archivo != null) {
                archivos.add(archivo)
            }
            success = saveFile(res.second , pair.first, user)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in storePhotoRelatedData", ex)
        }
        return success
    }

    private suspend fun saveFile(
        file: File,
        photoEntity: PhotoEntity,
        user: UserProfile
    ): Boolean {
        var success = false
        try {
            LogInfo("Entered saveFile")
            if (context.isDeviceIsConnectedToTheInternet()) {
                success = uploadFile(file, photoEntity, user)
            } else {
                LogInfo("No Internet connection saving file in db")
                photoEntity.fotoFlgSync = SyncFlgStateType.Pendiente.toString()
                storePhotosInDBUseCase.invoke(listOf(photoEntity))
                withContext(Dispatchers.Main) {
                    context.customToast(
                        CustomToast(
                            "No Internet Connection...", duration = Toast.LENGTH_LONG
                        )
                    )
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in saveFile", ex)
        }
        return success
    }

    private suspend fun uploadFile(
        file: File,
        photoEntity: PhotoEntity,
        user: UserProfile
    ): Boolean {
        var upflag = insertWithMultiPartUseCase.invoke(user, file)

        if (upflag) {
            //delete sinced photo
            deletePhotoByIdUseCase.invoke(user.empId.toInt(), photoEntity.fotoID)
            LogInfo("Photo sended")
        } else {
            photoEntity.fotoFlgSync = SyncFlgStateType.Pendiente.toString()
            storePhotosInDBUseCase.invoke(listOf(photoEntity))

            withContext(Dispatchers.Main) {
                context.customToast(
                    CustomToast(
                        "Ha ocurrido un error enviando la imagen",
                        duration = Toast.LENGTH_LONG,
                        picId = MessageTypeIcon.ERROR.IconType(),
                        titleToast = MessageTypeIcon.ERROR.toString()
                    )
                )
            }
            LogInfo("Error sending photo")
        }
        return upflag
    }

    /**
     * Call the whole lifecycle for the creation of a archive.
     * Steps: 1 - Attempt to create in ws
     *        2 - If success store in database with the document flag marked as sync
     *        3 - If fail store in database with the document flag marked as pending'
     */
    private suspend fun insertArchivesListEntireLifeCycle(archivo: List<Archivo>, storeInDB: Boolean, user: UserProfile): WsRespuesta? {
        return try {
            insertArchivosListApiAndDBUseCase.invoke(user, archivo, storeInDB)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in insertArchivosListEntireLifeCycle", ex)
            null
        }
    }
}