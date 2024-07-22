package com.camera.domain.useCases

import android.content.Context
import com.camera.data.models.entities.PhotoEntity
import com.camera.data.repository.PhotosRepository
import javax.inject.Inject

class StorePhotosInDBUseCase @Inject constructor(private val photosRepository: PhotosRepository) {

    suspend operator fun invoke(data: List<PhotoEntity>, context: Context) =
        photosRepository.storePhotosInDB(data, context)

}