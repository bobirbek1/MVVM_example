package com.example.restapi.ui.fragments.post

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restapi.database.MyDao
import com.example.restapi.ui.fragments.listeners.DataListener

class PostViewModelFactory(private val context: Context, private val dataListener: DataListener) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(context, dataListener) as T
        } else {
            throw IllegalArgumentException("PostModelClass not found")
        }
    }

}