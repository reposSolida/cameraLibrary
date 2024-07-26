package com.camera.domain.useCases

import com.camera.data.repository.PhotosRepository
import javax.inject.Inject

class GetAllPhotosSyncPendingFilesDBUseCase @Inject constructor(private val photosRepository: PhotosRepository) {

    suspend operator fun invoke() =
        photosRepository.getAllPhotosSyncPendingFiles()

}