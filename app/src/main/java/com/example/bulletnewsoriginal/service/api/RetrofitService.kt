package com.example.bulletnewsoriginal.service.api

import com.example.bulletnewsoriginal.model.NewsDataClass
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    private val BASEURL : String = "https://newsapi.org/v2/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(NewsAPI::class.java)

    fun getSingleForTopHeadlines():Single<NewsDataClass>{
        return retrofit.getTopHeadlines()
    }

    suspend fun getSingleForEverything(topic : String):Response<NewsDataClass>{
        return retrofit.getEverythingWithTopic(topic = topic)
    }


}