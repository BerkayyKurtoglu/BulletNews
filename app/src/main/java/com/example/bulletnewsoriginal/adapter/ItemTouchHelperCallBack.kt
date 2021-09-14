package com.example.bulletnewsoriginal.adapter

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.service.savingNewsDatabase.SavingNewsDatabase
import com.example.bulletnewsoriginal.viewModel.BaseViewModel
import kotlinx.coroutines.*

class ItemTouchHelperCallBack(
    val list : ArrayList<Article>,
    val adapter: SavedNewsRecyclerViewAdapter,
    val context: Context) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val savingNewsDao = SavingNewsDatabase(context).accessSavingNewsDao()
        list.clear()
        CoroutineScope(Dispatchers.Default).launch {
            list.addAll(savingNewsDao.getAllSavedNews())
            val swipedArticleId = list[viewHolder.layoutPosition].id
            savingNewsDao.deleteCertainNews(swipedArticleId)
            val newList = savingNewsDao.getAllSavedNews()
            withContext(Dispatchers.Main){
                adapter.notifyItemRemoved(viewHolder.layoutPosition)
                adapter.refreshAdapter(newList)
            }
        }
    }

    fun refreshList(newList: List<Article>){
        list.clear()
        list.addAll(newList)
    }


}