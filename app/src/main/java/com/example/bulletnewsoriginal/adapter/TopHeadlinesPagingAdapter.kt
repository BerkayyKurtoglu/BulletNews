package com.example.bulletnewsoriginal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.util.createPlaceHolder
import com.example.bulletnewsoriginal.util.uploadImageFromUrl
import kotlinx.android.synthetic.main.view_pager_item_home_fragment.view.*

class TopHeadlinesPagingAdapter(
    val callBack: DiffUtil.ItemCallback<Article> = CallBack
) : PagingDataAdapter<Article, TopHeadlinesPagingAdapter.ViewHolder>(callBack) {
    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.viewPager_CardView_NewsImageView.uploadImageFromUrl(item?.urlToImage ?: "",
            createPlaceHolder(holder.view.context))
        holder.view.viewPager_CardView_TitleTextView.text = item?.title ?: ""
        holder.view.viewPager_CardView_SourceTextView.text = item?.source?.name ?: ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item_home_fragment,parent,false)
        return ViewHolder(view)
    }

}

object CallBack : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}