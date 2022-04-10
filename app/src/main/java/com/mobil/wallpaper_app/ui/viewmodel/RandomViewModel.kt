package com.mobil.wallpaper_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mobil.wallpaper_app.repository.RandomDataSource

class RandomViewModel : ViewModel() {

    val flow = Pager(PagingConfig(15) ){
        RandomDataSource()
    }.flow.cachedIn(viewModelScope)

//    fun fetchRandomImages(): LiveData<List<ImageModel>> {
//        val liveData = MutableLiveData<List<ImageModel>>()
//        viewModelScope.launch {
//            val response = ApiClient.apiService.getRandomPhotos(1)
//            if (response.isSuccessful) {
//                liveData.value = response.body()
//            }
//
//        }
//        return liveData
//    }
}