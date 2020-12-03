package com.example.restapi.ui.fragments.posts

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restapi.database.MyDao
import com.example.restapi.ui.fragments.listeners.DataListener

class PostsViewModelFactory(private val context: Context, private val dataListener: DataListener) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            return PostsViewModel(context, dataListener) as T
        } else {
            throw IllegalArgumentException("PostsViewModel class not found")
        }
    }

}