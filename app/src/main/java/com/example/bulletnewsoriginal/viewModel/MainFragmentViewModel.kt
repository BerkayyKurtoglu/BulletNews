package com.example.bulletnewsoriginal.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bulletnewsoriginal.model.CategoryDatabaseItem
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.service.CategoryDatabase
import com.example.bulletnewsoriginal.service.RetrofitService
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

    val subNewsLiveData = MutableLiveData<ArrayList<NewsDataClass>>()
    private val subNewsList = ArrayList<NewsDataClass>()

    fun getTotalNews(){
        loadingStatuLiveData.value = false
        getTopHeadlines()
        getSubNews()
    }

    private fun getSubNews(){
        subNewsList.clear()
        println("From Function")
        /*launch {
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
        }*/

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
        super.onCleared()
    }

}