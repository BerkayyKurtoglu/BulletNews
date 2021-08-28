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
        val swipedArticleId = list[viewHolder.adapterPosition].id
        println(swipedArticleId)
        CoroutineScope(Dispatchers.Default).launch {
            savingNewsDao.deleteCertainNews(swipedArticleId)
            withContext(Dispatchers.Main){
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                adapter.refreshAdapter(savingNewsDao.getAllSavedNews())
            }
        }
    }

    fun refreshList(newList: List<Article>){
        list.clear()
        list.addAll(newList)
    }


}