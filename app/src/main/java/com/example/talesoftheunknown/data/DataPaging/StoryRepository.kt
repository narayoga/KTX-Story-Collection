package com.example.talesoftheunknown.data.DataPaging

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.talesoftheunknown.data.Database.StoryDatabase
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem
import com.example.talesoftheunknown.data.Model.UserRepository
import com.example.talesoftheunknown.data.Retrofit.ApiService

//class StoryRepository (private val storyDatabase: StoryDatabase, private val apiService: ApiService, private val repository: UserRepository) {
//    fun getPagingStory(): LiveData<PagingData<StoryResponse>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            pagingSourceFactory = {
//                StorySource(apiService, repository)
//            }
//        ).liveData
//    }
//}

class StoryRepository (private val storyDatabase: StoryDatabase, private val apiService: ApiService, private val repository: UserRepository) {
    fun getPagingStory(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, repository),
            pagingSourceFactory = {
//                StorySource(apiService, repository)
                storyDatabase.StoryDao().getAllStory()
            }
        ).liveData
    }
}