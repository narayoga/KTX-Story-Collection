package com.example.talesoftheunknown.data.Database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<ListStoryItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStory(vararg stories:ListStoryItem)

    @Query("SELECT * FROM stories")
    fun getAllStory(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM stories")
    suspend fun deleteAll()

}