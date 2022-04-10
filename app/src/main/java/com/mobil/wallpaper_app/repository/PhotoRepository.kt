package com.mobil.wallpaper_app.repository

import kotlinx.coroutines.flow.flow
import com.mobil.wallpaper_app.retrofit.ApiService

class PhotoRepository(private val apiService: ApiService) {

    fun getRandomPhoto(page: Int) = flow { emit(apiService.getRandomPhotos(page)) }

    fun getPhoto(topicId: String, page: Int, order_by: String) =
        flow { emit(apiService.getPhotos(topicId, page, order_by = order_by)) }
}