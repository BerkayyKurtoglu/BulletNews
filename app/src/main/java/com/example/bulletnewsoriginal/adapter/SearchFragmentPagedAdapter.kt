package com.example.bulletnewsoriginal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.util.createPlaceHolder
import com.example.bulletnewsoriginal.util.uploadImageFromUrl
import com.example.bulletnewsoriginal.view.SearchFragmentDirections
import kotlinx.android.synthetic.main.search_fragment_recycler_view_item.view.*

class SearchFragmentPagedAdapter(

) : PagingDataAdapter<Article, SearchFragmentPagedAdapter.ViewHolder>(DiffUtil) {

    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.view.searchFragment_viewPager_image.uploadImageFromUrl(article?.urlToImage ?: "",
            createPlaceHolder(holder.view.context))
        holder.view.searchFragment_viewPager_author.text = article?.author ?: ""
        holder.view.searchFragment_viewPager_title.text = article?.title ?: ""
        holder.view.searchFragment_wholeScreen.setOnClickListener {view->
            article?.let {
                Navigation.findNavController(view).navigate(SearchFragmentDirections.actionSearchFragmentToNewsDetailFragment2(article))
            } ?: Toast.makeText(view.context,"Something wrong !",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_fragment_recycler_view_item,parent,false)
        return ViewHolder(view)
    }
}

object DiffUtil : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}