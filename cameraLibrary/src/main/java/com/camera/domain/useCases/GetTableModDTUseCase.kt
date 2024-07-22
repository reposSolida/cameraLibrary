package com.camera.domain.useCases

import android.content.Context
import com.camera.data.repository.GenericRepository
import javax.inject.Inject

class GetTableModDTUseCase @Inject constructor(private val genericRepository: GenericRepository) {

    suspend operator fun invoke(empId: Int, tableName: String, context: Context) =
        genericRepository.getTableModDT(empId, tableName, context)?:"1900-01-01T00:00:00"

}