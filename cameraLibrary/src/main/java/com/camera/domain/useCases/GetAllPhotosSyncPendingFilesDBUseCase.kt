package com.camera.domain.useCases

import android.content.Context
import com.camera.data.repository.PhotosRepository
import javax.inject.Inject

class GetAllPhotosSyncPendingFilesDBUseCase @Inject constructor(private val photosRepository: PhotosRepository) {

    suspend operator fun invoke(context: Context) =
        photosRepository.getAllPhotosSyncPendingFiles(context)

}