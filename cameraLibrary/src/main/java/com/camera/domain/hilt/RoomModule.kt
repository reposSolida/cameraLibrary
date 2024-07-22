package com.camera.domain.hilt

import android.content.Context
import androidx.room.Room
import com.camera.data.dataBase.PreventaDatabase
import com.camera.utils.migration2to3
import com.camera.utils.migration3to4
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PreventaDatabase::class.java, "com.solidatec.preventav2.dba")
            .addMigrations(migration2to3)
            .addMigrations(migration3to4)
            .build()


    @Singleton
    @Provides
    fun provideArchivosDao(db: PreventaDatabase) = db.getArchivosDao()

    @Singleton
    @Provides
    fun providePhotosDao(db: PreventaDatabase) = db.getPhotosDao()

    @Singleton
    @Provides
    fun provideDocumentPhotosDao(db: PreventaDatabase) = db.getDocumentPhotosDao()

    @Singleton
    @Provides
    fun provideParametrosDao(db: PreventaDatabase) = db.getParametrosDao()

    @Singleton
    @Provides
    fun provideGenericDao(db: PreventaDatabase) = db.getGenericDao()

    @Singleton
    @Provides
    fun provideCategoriasDao(db: PreventaDatabase) = db.getCategoriasDao()

}