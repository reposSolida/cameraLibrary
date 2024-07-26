package com.camera.utils

object GlobalValues {
    const val SearchQueryTimeSpan = 3000L
    val defaultDeviceId: String = "1"
    val defaultToken: String = "lite.1234"
    val moduleMobile: String = "PREVENTA"

    val ID: String = "ID"

    var selectedEnviroment = GlobalEnviromentValues.BASE_URL_PREPRODUCTION

    val pre = "Preproducción"
    val vali = "Validación"
    val pro = "Producción"

    val enviromentsMap = mapOf<String, String>(
        pre to GlobalEnviromentValues.BASE_URL_PREPRODUCTION,
        vali to GlobalEnviromentValues.BASE_URL_VALIDATION,
        pro to GlobalEnviromentValues.BASE_URL_PRODUCTION
    )
}