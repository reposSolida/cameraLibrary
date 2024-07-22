package com.camera.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.camera.data.models.dao.ArchivosDao
import com.camera.data.models.dao.CategoriasDao
import com.camera.data.models.dao.DocumentsPhotosDao
import com.camera.data.models.dao.GenericDao
import com.camera.data.models.dao.ParametrosDao
import com.camera.data.models.dao.PhotosDao
import com.camera.data.models.entities.ArchivoEntity
import com.camera.data.models.entities.CategoriaEntity
import com.camera.data.models.entities.DocumentPhotosEntity
import com.camera.data.models.entities.ParametrosEntity
import com.camera.data.models.entities.PhotoEntity

@Database(
    version = 5,
    exportSchema = false,
    entities = [
        ArchivoEntity::class,
        PhotoEntity::class,
        DocumentPhotosEntity::class,
        ParametrosEntity::class,
        CategoriaEntity::class,
    ]
)
abstract class PreventaDatabase : RoomDatabase(){
    abstract fun getArchivosDao(): ArchivosDao

    abstract fun getPhotosDao(): PhotosDao

    abstract fun getDocumentPhotosDao(): DocumentsPhotosDao

    abstract fun getGenericDao(): GenericDao

    abstract fun getParametrosDao(): ParametrosDao

    abstract fun getCategoriasDao(): CategoriasDao
/*
    suspend fun deleteDatabase() {
        getArchivosDao().clearData()
        getCategoriasDao().clearData()
        getParametrosDao().clearData()
        getPhotosDao().clearData()
        getDocumentPhotosDao().clearData()
    }

    suspend fun deleteAllInactiveData() {
        getArchivosDao().clearInactiveData()
        getCategoriasDao().clearInactiveData()
        getPhotosDao().clearInactiveData()
        getDocumentPhotosDao().clearInativeData()
    }

 */
}