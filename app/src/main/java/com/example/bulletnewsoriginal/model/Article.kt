package com.example.bulletnewsoriginal.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @Embedded val source: @RawValue Source,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
