package com.example.restapi.ui.fragments.users

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restapi.model.User
import com.example.restapi.repositories.UserRepository
import com.example.restapi.ui.fragments.listeners.DataListener

class UsersViewModel(context: Context, dataListener: DataListener) : ViewModel() {

    private var userRepo: UserRepository? = null
    private var user: LiveData<List<User>>? = null

    init {
        userRepo = UserRepository.getInstance(context,dataListener)
        user = MutableLiveData()
    }

    fun fetchUsers() {
        user = userRepo?.getUsers()
    }

    fun getUsers(): LiveData<List<User>>? {
        return user
    }

}