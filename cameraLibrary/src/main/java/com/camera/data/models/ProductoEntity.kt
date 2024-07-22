package com.camera.data.models

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.camera.utils.LogError

@Entity(tableName = "productos", primaryKeys = ["EmpId", "ProdId"])
data class ProductoEntity(
    @ColumnInfo(name = "EmpId")
    val empID: Long = 0L,

    @ColumnInfo(name = "ProdId")
    val prodID: String = "",

    @ColumnInfo(name = "ProdDistId")
    val prodDistID: String? = "",

    @ColumnInfo(name = "ProdCorpId")
    val prodCorpID: String? = "",

    @ColumnInfo(name = "ProdDsc")
    val prodDsc: String? = "",

    @ColumnInfo(name = "ProdDscAbr")
    val prodDscABR: String? = "",

    @ColumnInfo(name = "ProdObs")
    val prodObs: String? = "",

    @ColumnInfo(name = "CategoriaId")
    val categoriaID: String = "",

    @ColumnInfo(name = "LineaId")
    val lineaID: String = "",

    @ColumnInfo(name = "MarcaId")
    val marcaID: String = "",

    @ColumnInfo(name = "PresentaId")
    val presentaID: String = "",

    @ColumnInfo(name = "VariedadId")
    val variedadID: String = "",

    @ColumnInfo(name = "PortafolioId")
    val portafolioID: String = "",

    @ColumnInfo(name = "ProdTipo")
    val prodTipo: String = "",

    @ColumnInfo(name = "ProdLleStock")
    val prodLleStock: String = "",

    @ColumnInfo(name = "UnimedId")
    var unimedID: String = "",

    @ColumnInfo(name = "ProdStkMin")
    val prodStkMin: Long = 0L,

    @ColumnInfo(name = "ProdStkCrit")
    val prodStkCrit: Long = 0L,

    @ColumnInfo(name = "ProdStkMax")
    val prodStkMax: Long = 0L,

    @ColumnInfo(name = "ProdAplComEsp")
    val prodAPLCOMEsp: Boolean,

    @ColumnInfo(name = "ProdPrjComEsp")
    val prodPrjCOMEsp: Long = 0L,

    @ColumnInfo(name = "ProdMargenPrcCompra")
    val prodMargenPrcCompra: Long = 0L,

    @ColumnInfo(name = "ProdLoteable")
    val prodLoteable: String = "",

    @ColumnInfo(name = "UninegId")
    val uninegID: String = "",

    @ColumnInfo(name = "ProdDscCorpId")
    val prodDscCorpID: String = "",

    @ColumnInfo(name = "modDT")
    val modDT: String = "",

    @ColumnInfo(name = "ProdHabCanjes")
    val prodHabCanjes: Long = 0L,

    @ColumnInfo(name = "ProdGpoCanjes")
    val prodGpoCanjes: String = "",

    @ColumnInfo(name = "ProdAlcohol")
    val prodAlcohol: String = "",

    @ColumnInfo(name = "ProdContabilidad")
    val prodContabilidad: String = "",

    @ColumnInfo(name = "ProdActivo")
    val prodActivo: String = "",

    @ColumnInfo(name = "PerfImpId")
    val perfImpID: String = "",

    @ColumnInfo(name = "PerfImpDsc")
    val perfImpDsc: String = "",

    @ColumnInfo(name = "PerfImpValor")
    val perfImpValor: Double = 0.0,

    @ColumnInfo(name = "ProdStkCnt")
    var prodStkCnt: Double = 0.0,

    @ColumnInfo(name = "ProdCodEanCSV")
    val prodCodEanCSV: String = "",

    @ColumnInfo(name = "ProdImagenURL")
    val prodImagenURL: String = "",

    @ColumnInfo(name = "ProdDscHTML")
    val prodDscHTML: String = "",

    @ColumnInfo(name = "ProdFlgEnvase")
    val prodFlgEnvase: String = "",

    @ColumnInfo(name = "PropietarioID")
    val propietarioID: String = "",

    @ColumnInfo(name = "PropietarioDsc")
    val propietarioDsc: String = "",

    @ColumnInfo(name = "ClaseProdId")
    val claseProdID: String = "",

    @ColumnInfo(name = "ClaseProdDsc")
    val claseProdDsc: String = "",

    @ColumnInfo(name = "SubClaseDsc")
    val subClaseDsc: String = "",

    @ColumnInfo(name = "SubClaseProdId")
    val subClaseProdID: String = "",

    @ColumnInfo(name = "ProdSyncFlg")
    val prodSyncFlg: String = "",

    @ColumnInfo(name = "ProdManVencimiento")
    val prodManVencimiento: String = "",

    @ColumnInfo(name = "ProdFchCosto")
    val prodFchCosto: String = "",

    @ColumnInfo(name = "ProdCostoUltimoMN")
    val prodCostoUltimoMN: Double = 0.0,

    @ColumnInfo(name = "ProdCostoUltimoME")
    val prodCostoUltimoME: Double = 0.0,

    @ColumnInfo(name = "ProdCostoPromedioMN")
    val prodCostoPromedioMN: Double = 0.0,

    @ColumnInfo(name = "ProdCostoPromedioME")
    val prodCostoPromedioME: Double = 0.0,

    @ColumnInfo(name = "ProdUnidadesxCaja")
    val prodUnidadesxCaja: Long = 0L,

    @ColumnInfo(name = "ProdCod3of9")
    val prodCod3Of9: String = "",

    @ColumnInfo(name = "ProdEstado")
    val prodEstado: String = "",

    @ColumnInfo(name = "txtGruposProductosRel")
    val txtGruposProductosRel: String = "",

    @ColumnInfo(name = "ProdGrpProdCombo")
    val prodGrpProdCombo: String = ""

) {
    fun fixedTxtGruposProductosRel(context: Context): List<String> {
        val list = mutableListOf<String>()
        try {
            val array = txtGruposProductosRel.split(";")
            list.addAll(array.filter { it.trim().isNotEmpty() })
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in fixedTxtGruposProductosRel", ex, context)
        }
        return list
    }

}