 package com.example.restapi.network


import com.example.restapi.model.Comment
import com.example.restapi.model.Post
import com.example.restapi.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("posts")
    fun fetchPosts(): Call<List<Post>>

    @GET("users")
    fun fetchUsers(): Call<List<User>>



    @GET("comments?postId=")
    fun fetchComments(@Query("postId") postId: Int): Call<List<Comment>>

    @GET("users?id=")
    fun fetchUserById(@Query("id") userId: Int): Call<List<User>>

    @GET("posts?userId=")
    fun fetchPostsByUserId(@Query("userId") userId: Int): Call<List<Post>>

}