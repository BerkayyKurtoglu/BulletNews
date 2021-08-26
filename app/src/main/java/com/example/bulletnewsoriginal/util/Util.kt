package com.example.bulletnewsoriginal.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.transform.RoundedCornersTransformation

fun ImageView.uploadImageFromUrl(uri : String, circularProgressDrawable: CircularProgressDrawable){

    this.load(uri = uri){
        crossfade(true)
        crossfade(500)
        placeholder(circularProgressDrawable)
        transformations(RoundedCornersTransformation(15f))
    }
}

fun createPlaceHolder(context : Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImageWithBinding(imageView: ImageView,uri : String?){
    uri?.let {
        imageView.uploadImageFromUrl(uri, createPlaceHolder(imageView.context))
    }
}
