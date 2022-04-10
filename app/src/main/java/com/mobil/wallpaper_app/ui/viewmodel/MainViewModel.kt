package com.mobil.wallpaper_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobil.wallpaper_app.retrofit.ApiClient
import com.mobil.wallpaper_app.room.entity.TopicModel
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    fun fetchTopics(): LiveData<List<TopicModel>> {
        val liveData = MutableLiveData<List<TopicModel>>()
        viewModelScope.launch {
            val response = ApiClient.apiService.getTopics()
            if (response.isSuccessful) {
                liveData.value = response.body()
            }

        }
        return liveData
    }
}