package com.camera.domain.useCases

import android.content.Context
import com.camera.data.repository.CategoriasRepository
import javax.inject.Inject
class GetAllCategoriasDBUseCase @Inject constructor(val categoriasRepository: CategoriasRepository) {

    suspend operator fun invoke(context: Context) =
        categoriasRepository.getAllCategoriesDB(context)
}