package com.camera.domain.useCases

import com.camera.data.repository.ArchivosRepository
import javax.inject.Inject
class GetAllSyncPendingFilesDBUseCase @Inject constructor(private val archivosRepository: ArchivosRepository) {

    suspend operator fun invoke() =
        archivosRepository.getAllSyncPendingFiles()
}