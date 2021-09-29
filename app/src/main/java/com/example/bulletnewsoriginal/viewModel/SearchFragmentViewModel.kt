package com.example.bulletnewsoriginal.viewModel

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.pagingsources.SearchPagingSource
import com.example.bulletnewsoriginal.service.api.RetrofitService
import kotlinx.coroutines.launch

class SearchFragmentViewModel(application: Application) : BaseViewModel(application=application) {

    private val retrofitService = RetrofitService()

    val newsLiveData = MutableLiveData<List<NewsDataClass>>()
    val progressLiveData = MutableLiveData<Boolean>()

    private val searchNewsCategories = ArrayList<String>()
    private val searchNewsResponseList = ArrayList<NewsDataClass>()

    private val _searchNewsData = MutableLiveData<NewsDataClass>()
    val searchNewsData : LiveData<NewsDataClass> = _searchNewsData

    fun searchNews(q : String?){
        launch {
            val response = retrofitService.getSingleForEverything(q ?: "")
            if (response.isSuccessful){
                response.body()?.let {
                    _searchNewsData.value = it
                }
            }
        }
    }

    fun searchPagedNews(q : String) = Pager(config = PagingConfig(pageSize = 10)){
        SearchPagingSource(retrofitService,q) }.flow.cachedIn(viewModelScope)

    fun getNewsForSearchFragment(connectivityManager : ConnectivityManager){
        progressLiveData.value = true
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED){
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
        }else{
            Toast.makeText(getApplication(), "Upps 🤔 looks like, you are not connected", Toast.LENGTH_LONG).show()
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