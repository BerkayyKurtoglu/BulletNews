package com.example.bulletnewsoriginal.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.service.savingNewsDatabase.SavingNewsDatabase
import kotlinx.coroutines.launch

class SavedNewsFragmentViewModel(application: Application) : BaseViewModel(application) {

    private val dao = SavingNewsDatabase(application).accessSavingNewsDao()
    val articlesLiveData = MutableLiveData<List<Article>>()
    val progressLiveData = MutableLiveData<Boolean>()

    fun getSavedArticles(){
        progressLiveData.value = true
        launch {
            articlesLiveData.value = dao.getAllSavedNews()
            progressLiveData.value = false
        }
    }
}