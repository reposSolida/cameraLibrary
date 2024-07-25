package com.camera.domain.useCases

import com.camera.data.models.Archivo
import com.camera.data.models.UserProfile
import com.camera.data.repository.ArchivosRepository
import javax.inject.Inject

class InsertArchivosApiAndDBUseCase @Inject constructor(private val archivosRepository: ArchivosRepository) {

    suspend operator fun invoke(user: UserProfile,
                                archivos: Archivo,
                                storeInDb: Boolean = true) =
        archivosRepository.insertArchivosApiAndDB(user, archivos, storeInDb)
}