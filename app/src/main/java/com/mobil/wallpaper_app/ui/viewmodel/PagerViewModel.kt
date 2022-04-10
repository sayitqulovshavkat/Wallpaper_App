package com.mobil.wallpaper_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mobil.wallpaper_app.repository.PhotoDataSource



class PagerViewModel(private val topicId: String, private val order_by: String) : ViewModel() {

    val flow = Pager(PagingConfig(15)) {
        PhotoDataSource(topicId, order_by)
    }.flow.cachedIn(viewModelScope)
}