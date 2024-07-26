package com.camera.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.camera.utils.SyncFlgStateType

@Entity(tableName = "documents_photos", primaryKeys = ["empId", "tpoDocId", "docUsuId", "docId"])
data class DocumentPhotosEntity(
    @ColumnInfo(name = "empId")
    val empId: Int,
    @ColumnInfo(name = "tpoDocId")
    val tpoDocId: String,
    @ColumnInfo(name = "docUsuId")
    val docUsuId: String,
    @ColumnInfo(name = "docId")
    val docId: String,
    @ColumnInfo(name = "photoIds")
    val photoIds: String,
    @ColumnInfo(name = "docPhotosSyncFlag")
    val docPhotosSyncFlag: String = SyncFlgStateType.Pendiente.toString(),
    @ColumnInfo(name = "docPhotosSyncError")
    val docPhotosSyncError: String = ""
)
