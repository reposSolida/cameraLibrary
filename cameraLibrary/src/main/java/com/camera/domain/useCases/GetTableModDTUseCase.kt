package com.camera.domain.useCases

import com.camera.data.repository.GenericRepository
import javax.inject.Inject

class GetTableModDTUseCase @Inject constructor(private val genericRepository: GenericRepository) {

    suspend operator fun invoke(empId: Int, tableName: String) =
        genericRepository.getTableModDT(empId, tableName)?:"1900-01-01T00:00:00"

}