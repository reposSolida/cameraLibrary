package com.camera.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camera.data.models.entities.ArchivoEntity

@Dao
interface ArchivosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(archivos: List<ArchivoEntity>)

    @Query("Select * from archivos where ArchFlgSync='P' or ArchFlgSync='E'")
    suspend fun getArchivosPendingToSync(): List<ArchivoEntity>


    @Query("Delete from archivos where ArchActivo='N'")
    suspend fun clearInactiveData(): Unit
}