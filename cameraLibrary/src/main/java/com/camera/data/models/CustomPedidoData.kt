package com.camera.data.models

import androidx.room.ColumnInfo

data class CustomPedidoData(
    @ColumnInfo(name = "MonedaIdPrecio")
    val monedaIdPrecio: String,
    @ColumnInfo(name = "PrecioImp")
    val precioImp: Double,
    @ColumnInfo(name = "PerfImpValor")
    val perfImpValor: Double,
    @ColumnInfo(name = "TpoCamCot")
    val tpoCamCot: Double,
    @ColumnInfo(name = "MonedaSimb")
    val monedaSimb: String,
)
