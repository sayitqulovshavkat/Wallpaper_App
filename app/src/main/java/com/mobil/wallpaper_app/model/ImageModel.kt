package com.mobil.wallpaper_app.model
import java.io.Serializable

data class ImageModel(
    val id: String,
    val height: Int,
    val links: Links,
    val urls: Urls,
    val user: User,
    val width: Int
) : Serializable