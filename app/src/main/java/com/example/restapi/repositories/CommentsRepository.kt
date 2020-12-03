package com.example.restapi.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.restapi.database.MyDao
import com.example.restapi.database.MyRoomDatabase
import com.example.restapi.memory.MemoryCache
import com.example.restapi.model.Comment
import com.example.restapi.network.ApiClient
import com.example.restapi.network.ApiInterface
import com.example.restapi.ui.fragments.listeners.DataListener
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsRepository(
    private val apiInterface: ApiInterface,
    private val dataSource: MyDao,
    private val dataListener: DataListener
) {

    private val TAG = "CommentRepository"

    companion object {
        private var INSTANCE: CommentsRepository? = null
        private var dataSource: MyDao? = null

        fun getInstance(context: Context, dataListener: DataListener): CommentsRepository {

            if (INSTANCE != null) {
                return INSTANCE!!
            }

            dataSource = MyRoomDatabase.getDatabase(context).dao()

            INSTANCE = CommentsRepository(
                ApiClient.getApiClient().create(ApiInterface::class.java),
                dataSource!!,
                dataListener
            )

            return INSTANCE!!

        }
    }

    fun getComments(postId: Int): LiveData<List<Comment>> {
        if (MemoryCache.getcomments() != null && postId == MemoryCache.getLastPostId()) {
            Log.d(TAG, "getComments: getCommentsFromMemory")
            return MemoryCache.getcomments()!!
        }
        MemoryCache.setComments(dataSource.getCommentsByPostId(postId), postId)
        Log.d(TAG, "getComments: getCommentsFromDatabase")
        return MemoryCache.getcomments()!!
    }

    fun fetchComments(postId: Int) {

        dataListener.onStartFetching()
        apiInterface.fetchComments(postId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(
                call: Call<List<Comment>>,
                response: Response<List<Comment>>
            ) {
                val res = response.body()
                Log.d(TAG, "onResponse:Comment: $res")
                if (response.isSuccessful && res != null) {
                    dataListener.onSuccess()
                    Log.d(TAG, "onResponse: $res")
                    CoroutineScope(Dispatchers.IO).launch {
                        dataSource.insertComments(res)
                    }
                } else {
                    dataListener.onFailure("${response.errorBody()}")
                    Log.e(TAG, "onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                dataListener.onFailure("${t.message}")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}