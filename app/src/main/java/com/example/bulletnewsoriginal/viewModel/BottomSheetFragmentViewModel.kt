package com.example.bulletnewsoriginal.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bulletnewsoriginal.model.CategoryDatabaseItem
import com.example.bulletnewsoriginal.service.categoryDatabase.CategoryDatabase
import kotlinx.coroutines.launch

class BottomSheetFragmentViewModel(
    application : Application
) : BaseViewModel(application = application) {

    private val savedNewsDAO = CategoryDatabase.invoke(application).categoryDao()
    private val databaseListMutable = MutableLiveData<List<CategoryDatabaseItem>>()
    val databaseList : LiveData<List<CategoryDatabaseItem>>
        get() = databaseListMutable

    

    init {

        viewModelScope.launch {
               val response = savedNewsDAO.getAllCategories()
                response.let {
                    databaseListMutable.postValue(response)
                }
        }
    }

}