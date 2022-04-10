package com.mobil.wallpaper_app.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class TopicModel(
    @PrimaryKey
    val id: String,
    val title: String
) : Serializable
