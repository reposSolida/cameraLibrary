package com.camera.data.repository

import android.content.Context
import androidx.sqlite.db.SimpleSQLiteQuery
import com.camera.data.models.dao.GenericDao
import com.camera.utils.GlobalTestValues
import com.camera.utils.LogError
import com.camera.utils.LogInfo
import javax.inject.Inject

class GenericRepository @Inject constructor(
    private val genericDao: GenericDao
) {
    suspend fun getTableModDT(empId: Int, tableName: String, context: Context): String? {
        return try {
            var stringBuilder =
                StringBuilder("SELECT modDT FROM $tableName where EmpId=$empId order by EmpId, modDT desc limit 1")
            LogInfo("QUERY: $stringBuilder", GlobalTestValues.debugMode)
            genericDao.getTableModDT(SimpleSQLiteQuery(stringBuilder.toString()))
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getCicloByEmpId: ", ex, context)
            null
        }
    }
}