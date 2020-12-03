package com.example.restapi.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.restapi.database.MyDao
import com.example.restapi.database.MyRoomDatabase
import com.example.restapi.memory.MemoryCache
import com.example.restapi.model.User
import com.example.restapi.network.ApiClient
import com.example.restapi.network.ApiInterface
import com.example.restapi.ui.fragments.listeners.DataListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val apiInterface: ApiInterface, private val dataSource: MyDao) {

    private val TAG = "UserRepository"

    companion object {
        private var INSTANCE: UserRepository? = null
        private var dataSource: MyDao? = null

        fun getInstance(context: Context, dataListener: DataListener): UserRepository {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            dataSource = MyRoomDatabase.getDatabase(context).dao()
            INSTANCE = UserRepository(
                ApiClient.getApiClient().create(ApiInterface::class.java),
                dataSource!!
            )
            return INSTANCE!!
        }

    }

    fun getUsers(): LiveData<List<User>> {
        if (MemoryCache.getNetworkAvailable() && MemoryCache.isFirstCallNetworkForUsers) {
            MemoryCache.isFirstCallNetworkForUsers = false
            fetchUsers()
        }
        if (MemoryCache.getUsers() != null) {
            return MemoryCache.getUsers()!!
        }
        MemoryCache.setUsers(dataSource.getUsers())
        return MemoryCache.getUsers()!!
    }

    fun getUsersById(userId: Int): LiveData<User> {
        if (MemoryCache.getUsers() != null && MemoryCache.getLastUserId() == userId) {
            return MemoryCache.getUserById()!!
        }
        MemoryCache.setUserById(dataSource.getUserById(userId), userId)
        return MemoryCache.getUserById()!!
    }


    fun fetchUsers() {

        apiInterface.fetchUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val res = response.body()
                if (response.isSuccessful && res != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataSource.insertUsers(res)
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

    }

    fun fetchUserById(userId: Int) {

        apiInterface.fetchUserById(userId).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val res = response.body()
                if (response.isSuccessful && res != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataSource.insertUsers(res)
                    }
                } else {
                    Log.e(TAG, "fetchUserById:onResponse: ${response.errorBody()}")
                }

            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "fetchUserById:onFailure: ${t.message}")
            }

        })
    }

}