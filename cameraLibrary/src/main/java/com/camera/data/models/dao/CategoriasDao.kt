package com.camera.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camera.data.models.entities.CategoriaEntity

@Dao
interface CategoriasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategorias(categoriasEntity: List<CategoriaEntity>)

    @Query("Select * from categorias order by CategoriaDsc ASC")
    suspend fun getAllCategories(): List<CategoriaEntity>

    @Query("Select * from categorias where CategoriaActiva='S' and CategoriaId in (:cats) order by CategoriaDsc ASC")
    suspend fun getAllCategoriesInFilter(cats: List<String>): List<CategoriaEntity>

    @Query("Select * from categorias where EmpId=:empId and CategoriaId=:catId order by CategoriaDsc ASC")
    suspend fun getAllCategoriesById(empId: Int, catId: String): CategoriaEntity

}