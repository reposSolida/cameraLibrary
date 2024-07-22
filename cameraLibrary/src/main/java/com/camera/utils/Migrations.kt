package com.camera.utils

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 ** Created by Carlos A. Novaez Guerrero on 11/10/2023 1:49 PM
 ** cnovaez.dev@outlook.com
 **/

val migration2to3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE documentos_pendientes ADD COLUMN DPCliIdOriginal VARCHAR NOT NULL DEFAULT ''")
    }
}

val migration3to4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE archivos ADD COLUMN ArchUsuId VARCHAR NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE archivos ADD COLUMN VenId VARCHAR NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE archivos ADD COLUMN CliId VARCHAR NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE archivos ADD COLUMN ArchLat VARCHAR NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE archivos ADD COLUMN ArchLong VARCHAR NOT NULL DEFAULT ''")
    }
}