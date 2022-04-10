package com.mobil.wallpaper_app.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(
    @PrimaryKey
    val id: String,
    val links: String,
    val urlsSmall: String,
    val urlsRegular: String,
    val userName: String,
    val height: Int,
    val width: Int
)