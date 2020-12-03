package com.example.restapi.ui.databinding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object DataBindingAdapter {

    private const val TAG = "DataBindingAdapter"

    @BindingAdapter("image")
    @JvmStatic
    fun setImage(imageView: ImageView, imagePath: String?) {
        Log.d(TAG, "setImage: $imagePath")
        if (imagePath != null)
            Log.d(TAG, "setImage: $imagePath")
        Glide.with(imageView.context)
            .load(imagePath)
            .into(imageView)
    }

}