package com.camera.data.models.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface GenericDao {

    @RawQuery
    suspend fun getTableModDT(queryString: SupportSQLiteQuery): String
}