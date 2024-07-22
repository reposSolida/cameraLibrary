package com.camera.domain.useCases

import android.content.Context
import com.camera.data.models.Archivo
import com.camera.data.models.UserProfile
import com.camera.data.repository.ArchivosRepository
import javax.inject.Inject

class InsertArchivosListApiAndDBUseCase @Inject constructor(private val archivosRepository: ArchivosRepository) {

    suspend operator fun invoke(
        context: Context,
        user: UserProfile,
        archivos: List<Archivo>,
        storeInDb: Boolean = true
    ) =
        archivosRepository.insertArchivosListApiAndDB(context, user, archivos, storeInDb)

}