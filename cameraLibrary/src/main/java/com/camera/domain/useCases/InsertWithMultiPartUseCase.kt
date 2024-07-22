package com.camera.domain.useCases

import com.camera.data.models.UserProfile
import com.camera.data.repository.EnvioImagenesMultiPartRepository
import java.io.File
import javax.inject.Inject

class InsertWithMultiPartUseCase @Inject constructor(
    private val envioImagenesMultiPartRepository: EnvioImagenesMultiPartRepository
)
{
    suspend operator fun invoke(userData : UserProfile, file: File): Boolean =
        envioImagenesMultiPartRepository.subirImagen(userData, file)

}