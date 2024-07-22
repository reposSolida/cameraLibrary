package com.camera.data.repository

import android.content.Context
import com.camera.data.models.dao.CategoriasDao
import com.camera.data.api.CategoriasApi
import com.camera.data.models.UserProfile
import com.camera.data.models.entities.CategoriaEntity
import com.camera.data.models.response.CategoriasResponse
import com.camera.data.models.response.WsRespuesta
import com.camera.domain.useCases.GetTableModDTUseCase
import com.camera.utils.LogError
import com.camera.utils.LogInfo
import com.camera.utils.getGeneralSP
import com.camera.utils.getPhoneImei
import com.camera.utils.isDeviceIsConnectedToTheInternet
import javax.inject.Inject
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.Response

class CategoriasRepository @Inject constructor(
    private val categoriasApi: CategoriasApi,
    private val categoriasDao: CategoriasDao,
    private val getTableModDTUseCase: GetTableModDTUseCase,
) {

    suspend fun getAllCategorias(context: Context, user: UserProfile): WsRespuesta {
        var wsResponse: WsRespuesta = WsRespuesta()
        try {
            if (context.isDeviceIsConnectedToTheInternet()) {
                LogInfo("calling getAllCategorias with user: ${user.username}  and company: ${user.empId} ")

                val res: Response<CategoriasResponse> =
                    categoriasApi.getCategorias(
                        com.camera.data.models.POSTPetitionBody(
                            user.username,
                            user.pass,
                            getPhoneImei(context) ?: "SINIMEI",
                            user.empId,
                            DtOper = if (getGeneralSP(context).modDT) getTableModDTUseCase.invoke(
                                user.empId.toInt(),
                                "categorias",
                                context
                            ) else ""
                        ).toMap()
                    )

                val response = res.body()
                val categorias = response?.categorias
                val resWs = response?.sdtWSRespuesta
            if (resWs != null) {
                wsResponse = resWs
            }
                if (categorias == null) {
                    LogInfo("No Categorias associated to User: $user")
                    if(!wsResponse.errdsc.isNullOrEmpty()){
                        LogError("Error code: ${wsResponse?.errcod}, Error description: ${wsResponse?.errdsc}", context)
                    }
                } else {

                    LogInfo("Inserting list of ${categorias.size} Categorias in the db")
                    storeCategoriasInDB(
                        categorias.map { it.toEntity() },
                        context
                    )
                    LogInfo("Categorias stored in database")
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllCategorias", ex, context)
        }

        return wsResponse
    }

    suspend fun storeCategoriasInDB(
        data: List<CategoriaEntity>,
        context: Context
    ) {
        try {
                categoriasDao.insertCategorias(data)
        } catch (ex: Exception) {
            LogError("Error in storeCategoriasInDB: ", ex, context)
        }

    }

    suspend fun getAllCategoriesDB(context: Context): List<CategoriaEntity> {
        return try {
            categoriasDao.getAllCategories()
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllCategoriesDB", ex, context)
            emptyList()
        }
    }

    suspend fun getAllCategoriesDBInFilter(cats: List<String>, context: Context): List<CategoriaEntity> {
        return try {
            categoriasDao.getAllCategoriesInFilter(cats)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllCategoriesDB", ex, context)
            emptyList()
        }
    }

    suspend fun getAllCategoriesDbById(empId: Int, catId: String, context: Context): CategoriaEntity? {
        return try {
            categoriasDao.getAllCategoriesById(empId, catId)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllCategoriesDB", ex, context)
            if (ex is TimeoutCancellationException) {
                throw ex
            }
            null
        }
    }


}