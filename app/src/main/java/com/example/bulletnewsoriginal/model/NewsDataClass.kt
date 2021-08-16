package com.example.bulletnewsoriginal.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class NewsDataClass(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
){
    var category : String? =null
}
