package com.example.restapi.ui.fragments.posts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restapi.database.MyDao
import com.example.restapi.model.Post
import com.example.restapi.repositories.PostsRepository
import com.example.restapi.ui.fragments.listeners.DataListener

class PostsViewModel(context: Context, dataListener: DataListener) : ViewModel() {

    private val TAG = "PostsViewModel"

    private var postsList:LiveData<List<Post>>? = null
    private var postsRepository:PostsRepository? = null

    init {
        postsRepository = PostsRepository.getInstance(context,dataListener)
        postsList = MutableLiveData()
    }

    fun fetchAllPosts(){
        postsList = postsRepository?.getPosts()
    }

    fun getPosts(): LiveData<List<Post>>? {
        return postsList
    }


}