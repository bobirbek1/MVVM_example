package com.example.restapi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restapi.model.Comment
import com.example.restapi.model.Post
import com.example.restapi.model.User

@Dao
interface MyDao {

    @Query("Select * from user Order by id ASC")
    fun getUsers(): LiveData<List<User>>

    @Query("Select * from post Order by id ASC")
    fun getPosts(): LiveData<List<Post>>

    @Query("Select * from comment where postId = :postId")
    fun getCommentsByPostId(postId: Int): LiveData<List<Comment>>

    @Query("Select * from user where id = :userId")
    fun getUserById(userId: Int): LiveData<User>

    @Query("Select * from post where userId = :userId")
    fun getPostsByUserId(userId: Int): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(listUsers: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(listPosts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(listComments: List<Comment>)
}