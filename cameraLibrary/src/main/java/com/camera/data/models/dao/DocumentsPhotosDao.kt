package com.camera.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camera.data.models.entities.DocumentPhotosEntity

@Dao
interface DocumentsPhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDocumentsPhotos(documents: List<DocumentPhotosEntity>)

    @Query("select * from documents_photos")
    fun getAllDocumentsPhotos(): List<DocumentPhotosEntity>

    @Query("select * from documents_photos where empId = :empId and tpoDocId = :tpoDocId and docUsuId = :docUsuId and docId = :docId")
    fun getDocumentsPhotosById(
        empId: Int,
        tpoDocId: String,
        docUsuId: String,
        docId: String
    ): List<DocumentPhotosEntity>

    @Query("select * from documents_photos where docPhotosSyncFlag = 'P'")
    fun getDocumentsPhotosPending(): List<DocumentPhotosEntity>
    @Query("select * from documents_photos where docPhotosSyncFlag = 'P' and empId = :empId and tpoDocId = :tpoDocId and docUsuId = :docUsuId and docId = :docId")
    fun getDocumentPhotosPendingById(empId: Int,
                                      tpoDocId: String,
                                      docUsuId: String,
                                      docId: String): DocumentPhotosEntity

    @Query("update documents_photos set docPhotosSyncFlag =:syncFlag and docPhotosSyncError = :syncFlagError where empId = :empId and tpoDocId = :tpoDocId and docUsuId = :docUsuId and docId = :docId")
    fun updateDocumentsPhotosSyncFlag(
        empId: Int,
        tpoDocId: String,
        docUsuId: String,
        docId: String,
        syncFlag: String,
        syncFlagError: String
    )

    @Query("delete from documents_photos where docPhotosSyncFlag = 'S' or docPhotosSyncFlag = 'E'")
    fun clearInativeData()

    @Query("delete from documents_photos")
    fun clearData()
}