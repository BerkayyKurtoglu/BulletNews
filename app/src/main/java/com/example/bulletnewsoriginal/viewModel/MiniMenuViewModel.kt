package com.example.bulletnewsoriginal.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.service.savingNewsDatabase.SavingNewsDatabase
import kotlinx.coroutines.launch

class MiniMenuViewModel(application: Application) : BaseViewModel(application) {

    private val savingNewsDao = SavingNewsDatabase(application).accessSavingNewsDao()

    val progressBarLiveData = MutableLiveData<Boolean>()

    fun saveNewsToRoom(article: Article){
        progressBarLiveData.value = true
        launch {
            savingNewsDao.insertToDatabase(article = article)
            progressBarLiveData.value = false
            Toast.makeText(getApplication(), "Saved successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllSavingNews(){
        launch {
            savingNewsDao.getAllSavedNews().forEach {
                println(it)
            }
        }
    }

    fun deleteCertainOrFullDatabase(id : Int?){
        launch {
            if (id == null){
                savingNewsDao.deleteEverything()
            }else{
                savingNewsDao.deleteCertainNews(id)
            }
        }
    }
}