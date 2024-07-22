package com.camera.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camera.data.models.entities.ParametrosEntity

@Dao
interface ParametrosDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfParametros(parametrosEntity: List<ParametrosEntity>)

    @Query("Select * from parametros where EmpId=:empId and Parid=:parId")
    suspend fun getParametroByParId(empId: Int, parId: String): ParametrosEntity

    @Query("Select * from parametros where EmpId=:empId and Parid=:parId and ParTxt LIKE '%'||:parTxt||'%'")
    suspend fun getParametroByParIdAndParTxt(
        empId: Int,
        parId: String,
        parTxt: String
    ): ParametrosEntity

}