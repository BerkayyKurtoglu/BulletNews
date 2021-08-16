package com.example.bulletnewsoriginal.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: @RawValue Source,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):Parcelable
