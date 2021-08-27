package com.example.bulletnewsoriginal.service.api

import com.example.bulletnewsoriginal.model.NewsDataClass
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines?country=us&apiKey=d28a8abf94324dcc8c2f8ff7e0d4cb84")
    fun getTopHeadlines() : Single<NewsDataClass>

    @GET("everything?language=en&apiKey=d28a8abf94324dcc8c2f8ff7e0d4cb84")
    suspend fun getEverythingWithTopic(@Query("q") topic : String) : Response<NewsDataClass>

}