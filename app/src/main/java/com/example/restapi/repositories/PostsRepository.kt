package com.example.restapi.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restapi.database.MyDao
import com.example.restapi.database.MyRoomDatabase
import com.example.restapi.memory.MemoryCache
import com.example.restapi.model.Post
import com.example.restapi.network.ApiClient
import com.example.restapi.network.ApiInterface
import com.example.restapi.ui.fragments.listeners.DataListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsRepository(
    private val apiInterface: ApiInterface,
    private val dataSource: MyDao,
    private val dataListener: DataListener
) {

    private val TAG = "PostRepository"


    companion object {
        private var INSTANCE: PostsRepository? = null
        private var dataSource: MyDao? = null

        fun getInstance(context: Context, dataListener: DataListener): PostsRepository? {
            if (INSTANCE != null) {
                return INSTANCE
            }
            dataSource = MyRoomDatabase.getDatabase(context).dao()
            INSTANCE = PostsRepository(
                ApiClient.getApiClient().create(ApiInterface::class.java),
                dataSource!!,
                dataListener
            )
            return INSTANCE
        }
    }

//    private var mApiInterface = apiInterface
//    private val typeData = MutableLiveData<MainTypesModel>()

    fun getPosts(): LiveData<List<Post>> {
        if (MemoryCache.getNetworkAvailable() && MemoryCache.isFirstCallNetworkForPosts) {
            MemoryCache.isFirstCallNetworkForPosts = false
            fetchPosts()
        }
        if (MemoryCache.getPosts() != null) {
            return MemoryCache.getPosts()!!
        }
        MemoryCache.setPosts(dataSource.getPosts())
        return MemoryCache.getPosts()!!
    }

    fun getPostsByUserId(userId: Int): LiveData<List<Post>> {
        if (MemoryCache.getNetworkAvailable()) {
            fetchPostsByUserId(userId)
        }
        if (MemoryCache.getPostsByUserId() != null && MemoryCache.getLastUserId() == userId) {
            return MemoryCache.getPostsByUserId()!!
        }
        MemoryCache.setPostsByUserId(dataSource.getPostsByUserId(userId), userId)
        return MemoryCache.getPostsByUserId()!!
    }

    fun fetchPosts() {

        apiInterface.fetchPosts().enqueue(object : Callback<List<Post>> {

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")

            }

            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {

                val res = response.body()
                Log.d(TAG, "onResponse: ${res.toString()}")
                if (response.code() == 200 && res != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataSource.insertPosts(res)
                    }
                } else {
                    Log.e(TAG, "onError: ${response.errorBody()}")
                }
            }
        })
    }

    fun fetchPostsByUserId(userId: Int): MutableLiveData<List<Post>> {
        val data = MutableLiveData<List<Post>>()

        apiInterface.fetchPostsByUserId(userId).enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val res = response.body()
                if (response.isSuccessful && res != null) {
                    data.value = res
                } else {
                    data.value = null
                    Log.e(TAG, "fetchPostsByUserId:onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                data.value = null
                Log.e(TAG, "fetchPostsByUserId:onFailure: ${t.message}")
            }

        })
        return data
    }


//    fun fetchAlbumsByUserId(userId: Int): LiveData<List<Album>> {
//        val data = MutableLiveData<List<Album>>()
//
//        apiInterface.fetchAlbumByUserId(userId)
//            .enqueue(object : Callback<List<Album>> {
//                override fun onResponse(
//                    call: Call<List<Album>>,
//                    response: Response<List<Album>>
//                ) {
//                    val res = response.body()
//                    Log.d(TAG, "album: $res")
//                    if (response.isSuccessful && res != null) {
//                        data.value = res
//                    } else {
//                        data.value = null
//                        Log.e(TAG, "onResponse: ${response.errorBody()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Album>>, t: Throwable) {
//                    data.value = null
//                    Log.e(TAG, "Album: ${t.message}")
//                }
//            })
//        return data
//    }
//

//    fun fetchPhotoByAlbumId(albumId: Int): LiveData<List<Photo>> {
//        val data = MutableLiveData<List<Photo>>()
//
//        apiInterface.fetchPhotoByAlbumId(albumId)
//            .enqueue(object : Callback<List<Photo>> {
//
//                override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
//                    val res = response.body()
//                    Log.d(TAG, "photo: $res")
//                    if (response.isSuccessful && res != null) {
//                        data.value = res
//                    } else {
//                        data.value = null
//                        Log.e(TAG, "onResponse: ${response.errorBody()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
//                    data.value = null
//                    Log.e(TAG, "Photo: ${t.message}")
//                }
//
//            })
//        return data
//    }
//
}