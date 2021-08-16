package com.example.bulletnewsoriginal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.util.createPlaceHolder
import com.example.bulletnewsoriginal.util.uploadImageFromUrl
import com.example.bulletnewsoriginal.view.SearchFragmentDirections
import kotlinx.android.synthetic.main.search_fragment_recycler_view_item.view.*

class SearchFragmentChildRecyclerViewAdapter(
    private val newsDataClass: NewsDataClass,
    private val context : Context
)
    : RecyclerView.Adapter<SearchFragmentChildRecyclerViewAdapter.ItemHolder>() {

    class ItemHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_fragment_recycler_view_item,parent,false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val article = newsDataClass.articles[position]
        article.author?.let {
            holder.view.searchFragment_viewPager_author.text = it
        }
        article.title?.let {
            holder.view.searchFragment_viewPager_title.text = it
        }
        article.urlToImage?.let {
            holder.view.searchFragment_viewPager_image.uploadImageFromUrl(it, createPlaceHolder(context))
        }

        holder.view.setOnClickListener {
            val extra = FragmentNavigatorExtras(holder.view.searchFragment_viewPager_image to "shared_element")
            Navigation.findNavController(it).navigate(
                SearchFragmentDirections.actionSearchFragmentToNewsDetailFragment2(article),
                extra)
        }

    }

    override fun getItemCount(): Int {
        return newsDataClass.articles.size
    }
}