package com.camera.domain.useCases

import android.content.Context
import com.camera.data.repository.PhotosRepository
import javax.inject.Inject

class DeletePhotoByIdUseCase @Inject constructor(private val photosRepository: PhotosRepository) {

    suspend operator fun invoke(empId: Int, photoId: String, context: Context) =
        photosRepository.deletePhotoById(empId, photoId, context)

}