package com.camera.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camera.data.models.entities.ArchivoEntity

@Dao
interface ArchivosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(archivos: List<ArchivoEntity>)

    @Query("Select * from archivos where ArchFlgSync='P' or ArchFlgSync='E'")
    suspend fun getArchivosPendingToSync(): List<ArchivoEntity>

    @Query("Select * from archivos where EmpId=:empId and ArchActivo='S' order by ArchivoId DESC")
    suspend fun getAllArchivos(empId: Int): List<ArchivoEntity>

    @Query("Delete from archivos where ArchActivo='N'")
    suspend fun clearInactiveData(): Unit

    @Query("Select * from archivos where EmpId=:empId and ArchCliID=:cliId and ArchActivo='S' ")
    suspend fun getArchivosByCliId(empId: Int, cliId: String): List<ArchivoEntity>

    @Query("Select * from archivos where EmpId=:empId and ArchTipo = 'ARCHIVO' and ArchActivo='S' and ArchCliID='' and ArchGpoCLi='' and (VenId=:idDeVendedorDelUsuario or  VenId='') ")
    suspend fun getArchivosDeEmpresa(empId: Int, idDeVendedorDelUsuario: String): List<ArchivoEntity>

    @Query("Select * from archivos where EmpId=:empId and ArchTipo = 'ARCHIVO' and ArchActivo='S' and (ArchCliID=:cliId or CliId=:cliId)  ")
    suspend fun getArchivosDeCliente(empId: Int, cliId: String): List<ArchivoEntity>

    @Query("Select * from archivos where EmpId=:empId and ArchTipo = 'FOTO' and ArchActivo='S'")
    suspend fun getFotosDeEmpresa(empId: Int): List<ArchivoEntity>
}