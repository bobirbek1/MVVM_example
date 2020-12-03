package com.example.restapi.ui.fragments.listeners

interface DataListener {
    fun onSuccess()
    fun onStartFetching()
    fun onFailure(error:String)
}