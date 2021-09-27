package com.example.bulletnewsoriginal.viewModel

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.bulletnewsoriginal.model.CategoryDatabaseItem
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.pagingsources.TopHeadlinesPagingSource
import com.example.bulletnewsoriginal.service.categoryDatabase.CategoryDatabase
import com.example.bulletnewsoriginal.service.api.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class MainFragmentViewModel(application: Application) : BaseViewModel(application) {

    private val categoryDatabaseDao = CategoryDatabase.invoke(getApplication()).categoryDao()
    private val retrofitService = RetrofitService()
    private val compositeDisposable = CompositeDisposable()

    val loadingStatuLiveData = MutableLiveData<Boolean>()
    val errorStatuLiveData = MutableLiveData<Boolean>()
    val topHeadLinesLiveData = MutableLiveData<NewsDataClass>()

    val job : CoroutineScope = viewModelScope

    val topHeadlines = Pager(
        config = PagingConfig(
            pageSize = 10
        )
    ){
        TopHeadlinesPagingSource(retrofitService)
    }.liveData.cachedIn(job)

    val subNewsLiveData = MutableLiveData<ArrayList<NewsDataClass>>()
    private val subNewsList = ArrayList<NewsDataClass>()

    fun getTotalNews(connectivityManager: ConnectivityManager){
        loadingStatuLiveData.value = true
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED){
            getTopHeadlines()
            getSubNews()
        }else{
            Toast.makeText(getApplication(), "Upps 🤔 looks like, you are not connected", Toast.LENGTH_LONG).show()
        }
    }

    private fun getSubNews(){
        subNewsList.clear()
        println("From Function")
        launch {
            if (categoryDatabaseDao.getAllCategories().isEmpty()){
                println("There is no data")
                loadingStatuLiveData.postValue(false)
            }else{
                categoryDatabaseDao.getAllCategories().forEach {
                    val category = it.category
                    //println(it)
                    val response = retrofitService.getSingleForEverything(category)
                    if (response.isSuccessful){
                        response.body()?.let {
                            it.category = category
                            subNewsList.add(it)
                        }
                    }
                }
                loadingStatuLiveData.postValue(false)
                subNewsLiveData.value = subNewsList
            }
        }
    }

    private fun getTopHeadlines(){
        compositeDisposable.add(retrofitService.getSingleForTopHeadlines()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<NewsDataClass>(){
                override fun onSuccess(t: NewsDataClass) {
                    topHeadLinesLiveData.postValue(t)
                }
                override fun onError(e: Throwable) {
                    println("there is problem")
                }
            }))
    }

    fun insertCategoryToRoom(category: CategoryDatabaseItem){
        launch {
            categoryDatabaseDao.insertCategory(category = category)
        }
    }

    fun deleteCategory(category : String){
        launch {
            categoryDatabaseDao.deleteCategory(category = category)
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        job.cancel()
        super.onCleared()
    }

}