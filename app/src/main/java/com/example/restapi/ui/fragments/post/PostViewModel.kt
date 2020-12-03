package com.example.restapi.ui.fragments.post

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restapi.database.MyDao
import com.example.restapi.model.Comment
import com.example.restapi.model.User
import com.example.restapi.repositories.CommentsRepository
import com.example.restapi.repositories.UserRepository
import com.example.restapi.ui.fragments.listeners.DataListener

class PostViewModel(context: Context,dataListener: DataListener) :
    ViewModel() {

    private val TAG = "PostViewModel"

    private var commentsRepository: CommentsRepository = CommentsRepository.getInstance(context, dataListener)
    private var userRepository: UserRepository = UserRepository.getInstance(context, dataListener)
    private var comments: LiveData<List<Comment>> = MutableLiveData()
    private var user: LiveData<User> = MutableLiveData()

    fun getComments(): LiveData<List<Comment>>? {
        return comments
    }

    fun getCommentsFromDB(postId:Int){
         comments = commentsRepository.getComments(postId)
    }

    fun getUserById(userId: Int) {
        user = userRepository.getUsersById(userId)
    }

    fun getUser(): LiveData<User>? {
        return user
    }

    fun fetchUserByIdFromNetwork(id:Int){
        userRepository.fetchUserById(id)
    }

    fun fetchCommentsByPostIdFromNetwork(postId:Int){
        commentsRepository.fetchComments(postId)
    }

}