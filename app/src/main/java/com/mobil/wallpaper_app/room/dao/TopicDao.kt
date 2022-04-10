package com.mobil.wallpaper_app.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mobil.wallpaper_app.room.entity.TopicModel

@Dao
interface TopicDao {

    @Insert(onConflict = REPLACE)
    fun addTopic(topicList: List<TopicModel>)

    @Query("select * from topicmodel")
    fun getAllTopic(): List<TopicModel>
}