package com.example.bulletnewsoriginal.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class NewsDataClass(
    val articles: @RawValue List<Article>,
    val status: String,
    val totalResults: Int
):Parcelable{
    var category : String? =null
}
