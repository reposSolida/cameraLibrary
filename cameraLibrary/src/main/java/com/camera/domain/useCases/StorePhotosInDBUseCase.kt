package com.camera.domain.useCases

import com.camera.data.models.entities.PhotoEntity
import com.camera.data.repository.PhotosRepository
import javax.inject.Inject

class StorePhotosInDBUseCase @Inject constructor(private val photosRepository: PhotosRepository) {

    suspend operator fun invoke(data: List<PhotoEntity>) =
        photosRepository.storePhotosInDB(data)

}