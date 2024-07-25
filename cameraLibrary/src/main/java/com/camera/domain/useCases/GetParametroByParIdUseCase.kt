package com.camera.domain.useCases

import com.camera.data.repository.ParametrosRepository
import javax.inject.Inject

class GetParametroByParIdUseCase @Inject constructor(private val parametrosRepository: ParametrosRepository) {

    suspend operator fun invoke(empId: Int, parId: String) =
        parametrosRepository.getParametroByParId(empId, parId)

}