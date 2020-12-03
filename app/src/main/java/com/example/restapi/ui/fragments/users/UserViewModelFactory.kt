package com.example.restapi.ui.fragments.users

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restapi.ui.fragments.listeners.DataListener

class UsersViewModelFactory(private val context: Context, private val dataListener: DataListener) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)){
            return UsersViewModel(context,dataListener) as T
        } else{
            throw IllegalArgumentException("UsersViewModel class not found")
        }
    }

}