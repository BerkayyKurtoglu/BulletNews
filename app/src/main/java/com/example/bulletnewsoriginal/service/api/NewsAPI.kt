package com.example.bulletnewsoriginal.service.api

import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.util.KEY
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines?country=us&apiKey=${KEY}")
    fun getTopHeadlines() : Single<NewsDataClass>

    @GET("everything?language=en&apiKey=${KEY}")
    suspend fun getEverythingWithTopic(@Query("q") topic : String) : Response<NewsDataClass>

    @GET("top-headlines?country=us&apiKey=${KEY}")
    suspend fun getResponseTopHeadlines(
        @Query("page") page : Int
    ) : Response<NewsDataClass>

}