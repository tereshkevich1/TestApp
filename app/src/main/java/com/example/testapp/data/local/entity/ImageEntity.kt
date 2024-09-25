package com.example.testapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "server_id")
    val serverId: String,
    @ColumnInfo(name = "image_url")
    val uri: String,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "lat")
    val lat: Double? = null,
    @ColumnInfo(name = "lng")
    val lng: Double? = null
)