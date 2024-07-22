package com.camera.domain.useCases

import android.content.Context
import com.camera.data.repository.ParametrosRepository
import javax.inject.Inject

class GetParametroByParIdUseCase @Inject constructor(val parametrosRepository: ParametrosRepository) {

    suspend operator fun invoke(empId: Int, parId: String, context: Context) =
        parametrosRepository.getParametroByParId(empId, parId, context)

}