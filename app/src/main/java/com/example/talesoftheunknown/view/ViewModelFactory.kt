package com.example.talesoftheunknown.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.talesoftheunknown.Injection
import com.example.talesoftheunknown.data.Model.UserRepository
import com.example.talesoftheunknown.data.DataPaging.StoryRepository
import com.example.talesoftheunknown.view.Add.AddViewModel
import com.example.talesoftheunknown.view.Auth.LoginViewModel
import com.example.talesoftheunknown.view.Main.MainViewModel
import com.example.talesoftheunknown.view.Maps.MapsViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: UserRepository, private val storyRepository: StoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository, storyRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddViewModel::class.java) -> {
                AddViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context, storyRepository: StoryRepository?): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = storyRepository?.let {
                        ViewModelFactory(
                            Injection.provideRepository(context),
                            it
                        )
                    }
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}