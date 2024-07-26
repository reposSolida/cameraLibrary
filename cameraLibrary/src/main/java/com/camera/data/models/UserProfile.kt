package com.camera.data.models

import com.camera.utils.GlobalEnviromentValues

data class UserProfile(
    val username: String,
    val pass: String,
    var environment: String = GlobalEnviromentValues.BASE_URL_PREPRODUCTION,
    val name: String,
    val empId: String,
    val empDsc: String,
    var lastSync: String = "2023-02-02",
    val currWeeK: String,
    val syncState: Boolean,
    val syncDesc: String,
    val sessionOpen: Boolean,
    val usuDepIdDft: String = "",
    var category: String = "",
    var sessionWasClosed: Boolean = false,
    var dayClosed: Boolean = false,
    var dayFinalized: Boolean = false,

    ) : java.io.Serializable {
    fun getUsersNameForFM(): String {
        return "Usuario: $username"
    }

    fun getNameForFM(): String {
        return "Nombre: $name"
    }

    fun getUserEnviromentForFM() = "Ambiente: ${getNameCurrentEnviroment()}"

    fun getUserCompanyForFM(): String {
        return "Empresa: $empDsc"
    }

    fun getLastSyncForFM(): String {
        return "Última sincronización: $lastSync"
    }

    private fun getNameCurrentEnviroment(): String {
        return when (environment) {
            GlobalEnviromentValues.BASE_URL_PREPRODUCTION -> "Preproducción"
            GlobalEnviromentValues.BASE_URL_PRODUCTION -> "Producción"
            GlobalEnviromentValues.BASE_URL_VALIDATION -> "Validación"
            else -> {
                "NA"
            }
        }
    }
}