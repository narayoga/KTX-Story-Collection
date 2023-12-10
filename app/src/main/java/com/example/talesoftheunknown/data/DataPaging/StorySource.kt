package com.example.talesoftheunknown.data.DataPaging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem
import com.example.talesoftheunknown.data.Model.UserRepository
import com.example.talesoftheunknown.data.Retrofit.ApiService
import kotlinx.coroutines.flow.first

//class StorySource (private val apiService: ApiService, private val repository: UserRepository) : PagingSource<Int, StoryResponse>() {
//
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, StoryResponse>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponse> {
//        return try {
//            println("masuk try StorySource ")
//            val userResponse = repository.getSession().first()
//            val token = userResponse.loginResult?.token
//            val position = params.key ?: INITIAL_PAGE_INDEX
//            val responseData = apiService.getStoriesWithPager(position, params.loadSize, "Bearer "+  token!! )
//
//            val dataList = responseData.toList()
//            LoadResult.Page(
//                data = dataList,
//                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
//                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
//            )
//        } catch (exception: Exception) {
//            println("masuk error StorySource $exception ")
//            exception.printStackTrace()
//            return LoadResult.Error(exception)
//        }
//    }
//}

//class StorySource (private val apiService: ApiService, private val repository: UserRepository) : PagingSource<Int, ListStoryItem>() {
//
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
//        return try {
//            val userResponse = repository.getSession().first()
//            val token = userResponse.loginResult?.token
//            val position = params.key ?: INITIAL_PAGE_INDEX
//            val responseData = apiService.getStoriesWithPager(position, params.loadSize, "Bearer "+  token!! )
//
//            val dataList = responseData.listStory?.toList()
//            LoadResult.Page(
//                data = dataList,
//                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
//                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
//            )
//        } catch (exception: Exception) {
//            println("masuk error StorySource $exception ")
//            exception.printStackTrace()
//            return LoadResult.Error(exception)
//        }
//    }
//}