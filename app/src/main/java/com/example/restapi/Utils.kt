package com.example.restapi

import android.content.Context
import android.widget.Toast


const val BUNDLE_POST = "post"
const val BUNDLE_USER = "user"

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}