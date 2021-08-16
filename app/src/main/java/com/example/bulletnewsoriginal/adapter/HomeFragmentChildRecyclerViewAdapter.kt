package com.example.bulletnewsoriginal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.util.createPlaceHolder
import com.example.bulletnewsoriginal.util.uploadImageFromUrl
import com.example.bulletnewsoriginal.view.HomeFragmentDirections
import kotlinx.android.synthetic.main.child_recycler_view_item.view.*

class HomeFragmentChildRecyclerViewAdapter(
    private val newsDataClass: NewsDataClass,
    private val context : Context
)
    : RecyclerView.Adapter<HomeFragmentChildRecyclerViewAdapter.ItemHolder>() {
    class ItemHolder(val view : View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_recycler_view_item,parent,false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val article = newsDataClass.articles[position]
        println("${newsDataClass.category}  : ${newsDataClass.status}")
        article.urlToImage?.let {
            holder.view.childRecycler_Image.uploadImageFromUrl(it, createPlaceHolder(context))
        }
        article.title?.let {
            holder.view.childRecycler_title_text.text = it
        }
        article.author?.let {
            holder.view.childRecycler_writer.text = it
        }

        holder.view.setOnClickListener {
            val extras = FragmentNavigatorExtras(holder.view.childRecycler_Image to "shared_element")
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeFragmentToNewsDetailFragment2(article),extras)
        }

    }

    override fun getItemCount(): Int {
        return 10
    }
}