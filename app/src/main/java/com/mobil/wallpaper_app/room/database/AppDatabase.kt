package com.mobil.wallpaper_app.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobil.wallpaper_app.room.dao.ImageDoa
import com.mobil.wallpaper_app.room.dao.TopicDao
import com.mobil.wallpaper_app.room.entity.ImageEntity
import com.mobil.wallpaper_app.room.entity.TopicModel

@Database(entities = [ImageEntity::class, TopicModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDoa
    abstract fun topicDao(): TopicDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .allowMainThreadQueries()
                    .build()

            }
            return instance!!
        }
    }
}