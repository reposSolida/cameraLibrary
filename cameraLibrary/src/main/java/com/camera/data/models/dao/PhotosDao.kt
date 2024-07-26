package com.camera.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camera.data.models.entities.PhotoEntity

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photos: List<PhotoEntity>)

    @Query("Select * from photos where FotoID in (:ids)")
    suspend fun getAllPhotosById(ids: List<String>): List<PhotoEntity>


    @Query("Select * from photos where FotoFlgSync='P' or FotoFlgSync='E'")
    suspend fun getPhotosPendingToSync(): List<PhotoEntity>

    @Query("Delete from photos where EmpId=:empId and FotoID=:photoId")
    suspend fun deletePhotoById(empId: Int, photoId: String)


}