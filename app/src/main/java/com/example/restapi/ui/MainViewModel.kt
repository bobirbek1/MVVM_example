package com.example.restapi.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restapi.repositories.PostsRepository
import com.example.restapi.model.Post
import com.example.restapi.model.User

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"

    private var post: LiveData<List<Post>>? = null
    private var user: LiveData<List<User>>? = null


    init {
        post = MutableLiveData()
        user = MutableLiveData()
    }
//
//    fun fetchPosts() {
//        post = postRepository?.fetchPosts()
//    }
//
//    fun getPosts(): LiveData<List<Post>>? {
//        return post
//    }
//
//    fun fetchUsers() {
//        user = postRepository?.fetchUsers()
//    }
//
//    fun getUsers(): LiveData<List<User>>? {
//        return user
//    }
//
//    //    private var statusesList: LiveData<MainTypesModel>? = null
////    private var prioritiesList: LiveData<MainTypesModel>? = null
////    fun fetchTypesData() {
////        typesList = mainRepo?.fetchAllTypes()
////        Log.d(TAG, "fetchTypesData: ${typesList?.value}")
////    }
////
////    fun fetchStatusesData() {
////        statusesList = mainRepo?.fetchAllStatuses()
////        Log.d(TAG, "fetchStatusesData: ${typesList?.value}")
////    }
////
////    fun fetchPrioritiesData() {
////        prioritiesList = mainRepo?.fetchAllPriorities()
////        Log.d(TAG, "fetchPrioritiesData: ${prioritiesList?.value}")
////    }
////
////
////    fun getMainTypesLiveData(): LiveData<MainTypesModel>? {
////        return typesList
////    }
////
////    fun getMainStatuseLiveData(): LiveData<MainTypesModel>? {
////        return statusesList
////    }
////
////    fun getMainPrioritiesLiveData(): LiveData<MainTypesModel>? {
////        return prioritiesList
////    }

}