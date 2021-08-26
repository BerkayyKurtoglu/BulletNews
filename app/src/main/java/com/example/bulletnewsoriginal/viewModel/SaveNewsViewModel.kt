package com.example.bulletnewsoriginal.viewModel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.service.savingNewsDatabase.SavingNewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveNewsViewModel(application: Application) : BaseViewModel(application) {

    private val savingNewsDao = SavingNewsDatabase(application).accessSavingNewsDao()
    val progressBarLiveData = MutableLiveData<Boolean>()

    fun saveNewsToRoom(article: Article){
        progressBarLiveData.value = true
        val list = ArrayList<Article>()
        val urlList = ArrayList<String?>()
        launch {
            list.clear()
            list.addAll(savingNewsDao.getAllSavedNews())
            if (list.isEmpty()){
                println("from is empty, saved")
                savingNewsDao.insertToDatabase(article = article)
                progressBarLiveData.value = false
                Toast.makeText(getApplication(), "Saved 👌", Toast.LENGTH_SHORT).show()
        }else{
                list.forEach {
                    urlList.add(it.url)
                }
                if (article.url in urlList){
                    Toast.makeText(getApplication(), "Already Saved 😉", Toast.LENGTH_SHORT).show()
                    progressBarLiveData.value = false
                }else{
                    Toast.makeText(getApplication(), "Saved 👌", Toast.LENGTH_SHORT).show()
                    savingNewsDao.insertToDatabase(article)
                    progressBarLiveData.value = false
                }
        } }
    }

}