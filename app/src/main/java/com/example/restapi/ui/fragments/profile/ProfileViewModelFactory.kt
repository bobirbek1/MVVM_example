package com.example.restapi.ui.fragments.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restapi.ui.fragments.listeners.DataListener

class ProfileViewModelFactory(
    private val context: Context,
    private val dataListener: DataListener
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(context, dataListener) as T
        } else {
            throw IllegalArgumentException("ProfileViewModel class not found")
        }
    }

}