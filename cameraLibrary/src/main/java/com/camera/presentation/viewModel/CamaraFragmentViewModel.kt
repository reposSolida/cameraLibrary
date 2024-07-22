package com.camera.presentation.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.camera.data.models.Archivo
import com.camera.data.models.UserProfile
import com.camera.data.models.entities.ArchivoEntity
import com.camera.data.models.entities.ParametrosEntity
import com.camera.data.models.entities.PhotoEntity
import com.camera.data.models.response.WsRespuesta
import com.camera.domain.useCases.DeletePhotoByIdUseCase
import com.camera.domain.useCases.GetAllCategoriasDBUseCase
import com.camera.domain.useCases.GetAllPhotosSyncPendingFilesDBUseCase
import com.camera.domain.useCases.GetParametroByParIdUseCase
import com.camera.domain.useCases.InsertArchivosListApiAndDBUseCase
import com.camera.domain.useCases.InsertWithMultiPartUseCase
import com.camera.domain.useCases.StorePhotosInDBUseCase
import com.camera.utils.SyncFlgStateType
import com.camera.utils.Utils
import com.camera.utils.AnimExtras
import com.camera.utils.CustomToast
import com.camera.utils.DataReturnType
import com.camera.utils.GeneralStateType
import com.camera.utils.LogError
import com.camera.utils.LogInfo
import com.camera.utils.MessageTypeIcon
import com.camera.utils.OnSyncFinishedListener
import com.camera.utils.customInformationMsgWithAnimation
import com.camera.utils.customToast
import com.camera.utils.isDeviceIsConnectedToTheInternet
import com.mylibrary.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CamaraFragmentViewModel @Inject constructor (
    private val getAllCategoriasDBUseCase: GetAllCategoriasDBUseCase,
    private val getParametroByParIdUseCase: GetParametroByParIdUseCase,
    private val getAllPhotosSyncPendingFilesDBUseCase: GetAllPhotosSyncPendingFilesDBUseCase,
    private val insertArchivosListApiAndDBUseCase: InsertArchivosListApiAndDBUseCase,
    private val storePhotosInDBUseCase: StorePhotosInDBUseCase,
    private val insertWithMultiPartUseCase: InsertWithMultiPartUseCase,
    private val deletePhotoByIdUseCase: DeletePhotoByIdUseCase,
    @ApplicationContext private val context: Context
    ) : ViewModel() {

        private lateinit var user: UserProfile

        fun setUser(user: UserProfile) = run { this.user = user }

        suspend fun getAllCategoriasDBUseCase() =
            getAllCategoriasDBUseCase.invoke(context)

        suspend fun getParamByParId(parId: String, ): ParametrosEntity? {
            return if (user.empId.isNotEmpty() && parId.isNotEmpty())
                getParametroByParIdUseCase.invoke(empId = user.empId.toInt(), parId = parId, context)
            else null
        }

    suspend fun syncPendingData(
        showNoInternetMsg: Boolean = true,
        listener: OnSyncFinishedListener? = null,
        returnedData: DataReturnType? = null,
        fragment: FragmentActivity
    ): Boolean {
        var syncedSuccessFull = false
        try {
            if (fragment.isDeviceIsConnectedToTheInternet()) {
                LogInfo("Sync all pending info")
                //syncPendingArchives(indenpendantCalls)
                syncPendingPhotoData()
                syncedSuccessFull = true
                LogInfo("Finish sennding all pending data")
                if (listener != null) {
                    LogInfo("Starting listener for onSyncFinished return")
                    listener.syncFinishedReturn(returnedData)
                }
            } else {
                withContext(Dispatchers.Main) {
                    if (showNoInternetMsg) context.customInformationMsgWithAnimation(
                        getString(context, R.string.no_internet_connection),
                        R.raw.anim_no_internet_kitty
                    )
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in syncPendingData", ex, context)
        }
        return syncedSuccessFull
    }

    suspend fun syncPendingPhotoData() {
        try {
            val pendingsPairs: MutableList<Pair<PhotoEntity, Bitmap>> = mutableListOf()
            val archivos: MutableList<ArchivoEntity> = mutableListOf()

            val photos = getAllPhotosSyncPendingFilesDBUseCase.invoke(context)

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
                            getString(context, R.string.sending_pending_photos_to_server_label),
                            R.raw.anim_sending_photos,
                            title = "Enviando foto $qty de $total, por favor espere...",
                            context = context
                        )
                        qty += 1
                    }
                    success = storePhotoRelatedData(pair, archivos)
                }

                LogInfo("Qty of archivos to store: ${archivos.size}")
                var wsRespuesta: WsRespuesta? = null
                if (archivos.isNotEmpty()) {
                    wsRespuesta = insertArchivesListEntireLifeCycle(archivos.map { it.toApi() }, true)
                }
                withContext(Dispatchers.Main) {
                    AnimExtras.dismissData()
                    if (wsRespuesta != null && wsRespuesta.ok == GeneralStateType.S.toString() && success) {
                        context.customToast(
                            CustomToast(
                                getString(context, R.string.pics_sended_label), duration = Toast.LENGTH_LONG
                            )
                        )
                    } else {
                        context.customToast(
                            CustomToast(
                                getString(context, R.string.was_not_possible_to_send_pics_label),
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
            LogError("Error in storePendingData", ex, context)
        }
    }

    suspend fun storePhotoRelatedData(
        pair: Pair<PhotoEntity, Bitmap>, archivos: MutableList<ArchivoEntity>
    ): Boolean {
        var success = false
        try {
            LogInfo("Attempting to storePhotoRelatedData", true)

            // Creo el archivo en el que almacenare la imagen en formato jpeg
            val res = Utils.enviarFoto(pair, context)

            // Convierto la imagen de bitmap a jpeg y la almaceno en el archivo creado
            Utils.saveFileOnly(pair.second, res.second, context)

            val archivo = Utils.generarRegistroArchivo(
                SyncFlgStateType.Pendiente.toString(),
                res.first,
                pair.first,
                user
            )
            if (archivo != null) {
                archivos.add(archivo)
            }
            success = saveFile(res.second , pair.first)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in storePhotoRelatedData", ex, context)
        }
        return success
    }

    private suspend fun saveFile(
        file: File,
        photoEntity: PhotoEntity
    ): Boolean {
        var success = false
        try {
            LogInfo("Entered saveFile")
            if (context.isDeviceIsConnectedToTheInternet()) {
                success = uploadFile(file, photoEntity)
            } else {
                LogInfo("No Internet connection saving file in db")
                photoEntity.fotoFlgSync = SyncFlgStateType.Pendiente.toString()
                storePhotosInDBUseCase.invoke(listOf(photoEntity), context)
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
            LogError("Error in saveFile", ex, context)
        }
        return success
    }

    private suspend fun uploadFile(
        file: File,
        photoEntity: PhotoEntity
    ): Boolean {
        var upflag = insertWithMultiPartUseCase.invoke(user, file)

        if (upflag) {
            //delete sinced photo
            deletePhotoByIdUseCase.invoke(user.empId.toInt(), photoEntity.fotoID, context)
            LogInfo("Photo sended")
        } else {
            photoEntity.fotoFlgSync = SyncFlgStateType.Pendiente.toString()
            storePhotosInDBUseCase.invoke(listOf(photoEntity), context)

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
    suspend fun insertArchivesListEntireLifeCycle(archivo: List<Archivo>, storeInDB: Boolean): WsRespuesta? {
        return try {
            insertArchivosListApiAndDBUseCase.invoke(context, user, archivo, storeInDB)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in insertArchivosListEntireLifeCycle", ex, context)
            null
        }
    }

}

