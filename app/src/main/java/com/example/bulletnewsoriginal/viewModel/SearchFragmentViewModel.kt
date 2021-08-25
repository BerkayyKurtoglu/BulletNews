package com.example.bulletnewsoriginal.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.service.api.RetrofitService
import kotlinx.coroutines.launch

class SearchFragmentViewModel(application: Application) : BaseViewModel(application=application) {

    private val retrofitService = RetrofitService()

    val newsLiveData = MutableLiveData<List<NewsDataClass>>()
    val progressLiveData = MutableLiveData<Boolean>()

    private val searchNewsCategories = ArrayList<String>()
    private val searchNewsResponseList = ArrayList<NewsDataClass>()

    fun getNewsForSearchFragment(){
        progressLiveData.value = true
        launch {
            searchNewsResponseList.clear()
            addArrayList()
            searchNewsCategories.forEach {
                val response = retrofitService.getSingleForEverything(it)
                if (response.isSuccessful){
                    response.body()?.let {
                        searchNewsResponseList.add(it)
                    }
                }
            }
            progressLiveData.value = false
            newsLiveData.postValue(searchNewsResponseList)
        }
    }

    private fun addArrayList(){
        searchNewsCategories.clear()
        searchNewsCategories.add("Health")
        searchNewsCategories.add("Politics")
        searchNewsCategories.add("Art")
        searchNewsCategories.add("Food")
        searchNewsCategories.add("Science")
        searchNewsCategories.add("Covid-19")
    }
}