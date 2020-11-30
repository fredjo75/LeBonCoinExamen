package com.example.leboncoinexamen.data.datasource.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album_table")
data class DbAlbum(
    val albumId: Int?,
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?,
)