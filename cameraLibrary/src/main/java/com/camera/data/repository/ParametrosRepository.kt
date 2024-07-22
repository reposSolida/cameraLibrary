package com.camera.data.repository

import android.content.Context
import com.camera.data.models.dao.ParametrosDao
import javax.inject.Inject
import com.camera.data.api.ParametrosApi
import com.camera.data.models.UserProfile
import com.camera.data.models.entities.ParametrosEntity
import com.camera.data.models.response.ParametrosResponse
import com.camera.data.models.response.WsRespuesta
import com.camera.domain.useCases.GetTableModDTUseCase
import com.camera.utils.LogError
import com.camera.utils.LogInfo
import com.camera.utils.getGeneralSP
import com.camera.utils.getPhoneImei
import com.camera.utils.isDeviceIsConnectedToTheInternet
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.Response

class ParametrosRepository @Inject constructor(
    private val parametrosApi: ParametrosApi,
    private val parametrosDao: ParametrosDao,
    private val getTableModDTUseCase: GetTableModDTUseCase
) {

    suspend fun getAllParametros(context: Context, user: UserProfile): WsRespuesta? {
        var wsResponse: WsRespuesta = WsRespuesta()
       
        try {
           
            if (context.isDeviceIsConnectedToTheInternet()) {
                LogInfo("calling getAllParametros with user: ${user.username} and pass $ ${user.pass} for company: ${user.empId}")

                val res: Response<ParametrosResponse> = parametrosApi.getParametros(
                    com.camera.data.models.POSTPetitionBody(
                        user.username,
                        user.pass,
                        getPhoneImei(context) ?: "SINIMEI",
                        user.empId,
                        DtOper = if (getGeneralSP(context).modDT) getTableModDTUseCase.invoke(
                            user.empId.toInt(),
                            "parametros",
                            context
                        ) else ""
                    ).toMap()
                )

                val response = res.body()
                val parametros = response?.parametros
                val resWs = response?.sdtWSRespuesta
            if (resWs != null) {
                wsResponse = resWs
            }
                if (parametros == null) {
                    LogInfo("No Parametros associated to User: $user")
                    LogError("Error code: ${wsResponse?.errcod}, Error deescription: ${wsResponse?.errdsc}", context)
                } else {

                    LogInfo("Inserting list of ${parametros.size} parametros in the db")
                    storeParametrosInDB(
                        parametros.map { it.toEntity() },
                        context
                    )
                    LogInfo("Parametros stored in database")
                }
            }
            

        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllParametros", ex, context)
        }

        return wsResponse
    }

    suspend fun storeParametrosInDB(
        data: List<ParametrosEntity>,
        context: Context
    ) {
        try {


                parametrosDao.insertListOfParametros(data)


        } catch (ex: Exception) {
            LogError("Error in storeParametrosInDB: ", ex, context)
            ex.printStackTrace()
        }
    }

    suspend fun getParametroByParId(
        empId: Int,
        parId: String,
        context: Context
    ): ParametrosEntity? {
        return try {
            parametrosDao.getParametroByParId(empId, parId)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getParametroByParId", ex, context)
            if (ex is TimeoutCancellationException || ex is CancellationException) {
                throw ex
            }
            null
        }
    }

    suspend fun getParametroByParIdAndParTxt(
        empId: Int,
        parId: String,
        parTxt: String,
        context: Context
    ): ParametrosEntity? {
        return try {
            parametrosDao.getParametroByParIdAndParTxt(empId, parId, parTxt)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getParametroByParIdAndParTxt", ex, context)
            null
        }

    }

}


