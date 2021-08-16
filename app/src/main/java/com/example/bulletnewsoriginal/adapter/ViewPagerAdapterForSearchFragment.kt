package com.example.bulletnewsoriginal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.model.NewsDataClass
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.view_pager_item_search_fragment.view.*
import retrofit2.Response
import javax.inject.Inject

class ViewPagerAdapterForSearchFragment(
    private val responseList: ArrayList<NewsDataClass>,
    private val context: Context)
    : RecyclerView.Adapter<ViewPagerAdapterForSearchFragment.ItemHolder>() {

    class ItemHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item_search_fragment,parent,false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        //val searchFragmentChildRecyclerViewAdapter = SearchFragmentChildRecyclerViewAdapter(newsDataClass = newsDataClass,context)
        val searchFragmentChildRecyclerViewAdapter = SearchFragmentChildRecyclerViewAdapter(responseList[position],context)
        holder.view.searchFragment_viewPager_recyclerView.layoutManager = LinearLayoutManager(context)
        holder.view.searchFragment_viewPager_recyclerView.adapter = searchFragmentChildRecyclerViewAdapter
    }

    override fun getItemCount(): Int {
        return responseList.size
    }


    fun replaceAdapter(newList : List<NewsDataClass>){
        responseList.clear()
        responseList.addAll(newList)
        this.notifyDataSetChanged()
    }
}