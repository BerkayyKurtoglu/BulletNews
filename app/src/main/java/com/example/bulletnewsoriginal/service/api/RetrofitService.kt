package com.example.bulletnewsoriginal.service.api

import android.content.Context
import android.content.pm.PackageManager
import com.example.bulletnewsoriginal.model.NewsDataClass
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService(val context: Context) {

    private val KEY = context.applicationContext.applicationInfo.metaData["keyValue"].toString()
    private val BASEURL : String = "https://newsapi.org/v2/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(NewsAPI::class.java)

    val client = OkHttpClient()

    fun getSingleForTopHeadlines():Single<NewsDataClass>{
        val client = OkHttpClient().newBuilder()
        client.writeTimeout(5, TimeUnit.MINUTES)
        client.readTimeout(5,TimeUnit.MINUTES)
        return retrofit.getTopHeadlines(KEY)
    }

    suspend fun getSingleForEverything(topic : String):Response<NewsDataClass>{
        val client = OkHttpClient().newBuilder()
        client.writeTimeout(5, TimeUnit.MINUTES)
        client.readTimeout(5,TimeUnit.MINUTES)
        return retrofit.getEverythingWithTopic(topic = topic)
    }


}