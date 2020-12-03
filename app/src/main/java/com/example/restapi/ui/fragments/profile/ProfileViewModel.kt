package com.example.restapi.ui.fragments.profile


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restapi.model.Post
import com.example.restapi.repositories.PostsRepository
import com.example.restapi.ui.fragments.listeners.DataListener

class ProfileViewModel(context: Context, dataListener: DataListener) : ViewModel() {

    private var postsRepository: PostsRepository? = null
    private var userPosts: LiveData<List<Post>>? = null


    init {
        postsRepository = PostsRepository.getInstance(context, dataListener)
        userPosts = MutableLiveData()
    }

    fun fetchPostsByUserId(userId:Int){
        userPosts = postsRepository?.getPostsByUserId(userId)
    }

    fun getPosts(): LiveData<List<Post>>? {
        return userPosts
    }

}