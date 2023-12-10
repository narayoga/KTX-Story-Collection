package com.example.talesoftheunknown.data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem
import com.example.talesoftheunknown.data.Model.Story.StoryResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(
    entities = [ListStoryItem::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ListStoryItemConverter::class)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun StoryDao():StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

class ListStoryItemConverter {
    @TypeConverter
    fun fromListStoryItemList(value: List<ListStoryItem?>?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toListStoryItemList(value: String?): List<ListStoryItem?>? {
        val listType = object : TypeToken<List<ListStoryItem?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}