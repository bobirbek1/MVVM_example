package com.example.restapi.memory

import androidx.lifecycle.LiveData
import com.example.restapi.model.Comment
import com.example.restapi.model.Post
import com.example.restapi.model.User

object MemoryCache {

    var isFirstCallNetworkForUsers = true
    var isFirstCallNetworkForPosts = true


    private var isNetworkAvailable = false

    private var lastPostId: Int = -1
    private var lastUserId: Int = -1

    private var commentsByAlbumId: LiveData<List<Comment>>? = null
    private var posts: LiveData<List<Post>>? = null
    private var postsByUserId: LiveData<List<Post>>? = null
    private var users: LiveData<List<User>>? = null
    private var userById: LiveData<User>? = null

    fun setNetworkAvailable(isNetworkAvailable: Boolean) {
        this.isNetworkAvailable = isNetworkAvailable
    }

    fun getNetworkAvailable(): Boolean {
        return isNetworkAvailable
    }

    fun getcomments(): LiveData<List<Comment>>? {
        return commentsByAlbumId
    }

    fun setComments(commentsByAlbumId: LiveData<List<Comment>>, postId: Int) {
        lastPostId = postId
        this.commentsByAlbumId = commentsByAlbumId
    }

    fun getPosts(): LiveData<List<Post>>? {
        return posts
    }

    fun setPosts(posts: LiveData<List<Post>>) {
        this.posts = posts
    }

    fun getPostsByUserId(): LiveData<List<Post>>? {
        return postsByUserId
    }

    fun setPostsByUserId(postsByUserId: LiveData<List<Post>>, userId: Int) {
        lastUserId = userId
        this.postsByUserId = postsByUserId
    }

    fun getUsers(): LiveData<List<User>>? {
        return users
    }

    fun setUsers(users: LiveData<List<User>>) {
        this.users = users
    }

    fun getLastUserId(): Int {
        return lastUserId
    }

    fun getLastPostId(): Int {
        return lastPostId
    }

    fun setUserById(user: LiveData<User>, userId: Int) {
        lastUserId = userId
        this.userById = user
    }

    fun getUserById(): LiveData<User>? {
        return userById
    }

}