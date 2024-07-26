package com.camera.domain.useCases

import com.camera.data.repository.CategoriasRepository
import javax.inject.Inject
class GetAllCategoriasDBUseCase @Inject constructor(private val categoriasRepository: CategoriasRepository) {

    suspend operator fun invoke() =
        categoriasRepository.getAllCategoriesDB()
}