package com.camera.presentation.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getString
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.camera.data.models.Archivo
import com.camera.data.models.UserProfile
import com.camera.data.models.UserSessionData
import com.camera.data.models.entities.ArchivoEntity
import com.camera.data.models.entities.ParametrosEntity
import com.camera.data.models.entities.PhotoEntity
import com.camera.data.models.response.WsRespuesta
import com.camera.domain.syncHelper.SyncHelper
import com.camera.domain.useCases.DeletePhotoByIdUseCase
import com.camera.domain.useCases.GetAllCategoriasDBUseCase
import com.camera.domain.useCases.GetAllPhotosSyncPendingFilesDBUseCase
import com.camera.domain.useCases.GetAllSyncPendingFilesDBUseCase
import com.camera.domain.useCases.GetParametroByParIdUseCase
import com.camera.domain.useCases.InsertArchivosApiAndDBUseCase
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
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CamaraFragmentViewModel @Inject constructor (
    private val getAllCategoriasDBUseCase: GetAllCategoriasDBUseCase,
    private val getParametroByParIdUseCase: GetParametroByParIdUseCase,
    private val insertArchivosListApiAndDBUseCase: InsertArchivosListApiAndDBUseCase,
    private val storePhotosInDBUseCase: StorePhotosInDBUseCase,
    private val insertWithMultiPartUseCase: InsertWithMultiPartUseCase,
    private val deletePhotoByIdUseCase: DeletePhotoByIdUseCase,
    private val syncHelper: SyncHelper,
    @ApplicationContext private val context: Context
    ) : ViewModel() {

        private lateinit var user: UserProfile

        fun setUser(user: UserProfile) = run { this.user = user }

        suspend fun getAllCategoriasDBUseCase() =
            getAllCategoriasDBUseCase.invoke()

        suspend fun getParamByParId(parId: String, ): ParametrosEntity? {
            return if (user.empId.isNotEmpty() && parId.isNotEmpty())
                getParametroByParIdUseCase.invoke(empId = user.empId.toInt(), parId = parId)
            else null
        }

    suspend fun syncPendingData(
        showNoInternetMsg: Boolean = true,
        listener: OnSyncFinishedListener? = null,
        returnedData: DataReturnType? = null,
        indenpendantCalls: Boolean = false
    ): Boolean {
        return syncHelper.syncPendingData(
            user,
            context,
            showNoInternetMsg,
            listener,
            returnedData,
            indenpendantCalls
        )
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
            LogError("Error in storePhotoRelatedData", ex)
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
        photoEntity: PhotoEntity
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
    suspend fun insertArchivesListEntireLifeCycle(archivo: List<Archivo>, storeInDB: Boolean): WsRespuesta? {
        return try {
            insertArchivosListApiAndDBUseCase.invoke(user, archivo, storeInDB)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in insertArchivosListEntireLifeCycle", ex)
            null
        }
    }

    fun createPhotoUri(): Uri {
        val photoName = "${Utils.getCurrentDateAndTime()}.jpg"
        val storageDir = context.getExternalFilesDir(null)
        val photoFile = File(storageDir, photoName)
        val authority = "${context.packageName}.fileprovider"
        return FileProvider.getUriForFile(
            context,
            authority,
            photoFile
        )
    }


    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val retVal: Bitmap
        val matrix = Matrix()
        matrix.postRotate(angle)
        retVal = Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
        return retVal
    }

}

