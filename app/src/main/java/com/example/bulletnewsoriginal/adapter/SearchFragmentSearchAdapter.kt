package com.example.bulletnewsoriginal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.util.createPlaceHolder
import com.example.bulletnewsoriginal.util.uploadImageFromUrl
import kotlinx.android.synthetic.main.search_fragment_recycler_view_item.view.*

class SearchFragmentSearchAdapter : RecyclerView.Adapter<SearchFragmentSearchAdapter.ViewHolder>() {
    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this,diffUtilCallback)
    var searchNews : List<Article>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_fragment_recycler_view_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = searchNews[position]
        holder.view.searchFragment_viewPager_image.uploadImageFromUrl(article.urlToImage ?: "",
            createPlaceHolder(holder.view.context))
        holder.view.searchFragment_viewPager_title.text = article.title ?: ""
        holder.view.searchFragment_viewPager_author.text = article.author ?: ""
    }

    override fun getItemCount(): Int {
        return searchNews.size
    }
}