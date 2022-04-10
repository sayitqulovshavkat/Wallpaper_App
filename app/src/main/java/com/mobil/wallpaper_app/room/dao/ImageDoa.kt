package com.mobil.wallpaper_app.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mobil.wallpaper_app.room.entity.ImageEntity

@Dao
interface ImageDoa {

    @Insert
    fun addImage(imageEntity: ImageEntity)

    @Query("DELETE FROM imageentity WHERE id = :imageId")
    fun deleteImage(imageId: String)

    @Query("select count(id) from imageentity where id like :id")
    fun getIsLiked(id: String): Int

    @Query("select * from imageentity")
    fun getAllImage(): List<ImageEntity>
}