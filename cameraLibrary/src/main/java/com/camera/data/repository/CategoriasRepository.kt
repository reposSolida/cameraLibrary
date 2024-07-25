package com.camera.data.repository

import com.camera.data.models.dao.CategoriasDao
import com.camera.data.models.entities.CategoriaEntity
import com.camera.utils.LogError
import javax.inject.Inject

class CategoriasRepository @Inject constructor(
    private val categoriasDao: CategoriasDao,
) {
    suspend fun getAllCategoriesDB(): List<CategoriaEntity> {
        return try {
            categoriasDao.getAllCategories()
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in getAllCategoriesDB", ex)
            emptyList()
        }
    }
}