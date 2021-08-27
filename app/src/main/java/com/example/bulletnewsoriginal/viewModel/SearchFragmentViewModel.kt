package com.example.bulletnewsoriginal.viewModel

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.service.api.RetrofitService
import kotlinx.coroutines.launch

class SearchFragmentViewModel(application: Application) : BaseViewModel(application=application) {

    private val retrofitService = RetrofitService(getApplication())

    val newsLiveData = MutableLiveData<List<NewsDataClass>>()
    val progressLiveData = MutableLiveData<Boolean>()

    private val searchNewsCategories = ArrayList<String>()
    private val searchNewsResponseList = ArrayList<NewsDataClass>()

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