package com.example.bulletnewsoriginal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.databinding.SavedNewRecyclerItemBinding
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.view.SavedNewsFragment
import com.example.bulletnewsoriginal.view.SavedNewsFragmentDirections

class SavedNewsRecyclerViewAdapter(
    private val articleList : ArrayList<Article>
    ): RecyclerView.Adapter<SavedNewsRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val binding : SavedNewRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =  SavedNewRecyclerItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.article = articleList[position]
        holder.binding.root.setOnClickListener {
            Navigation.findNavController(it).navigate(SavedNewsFragmentDirections.actionSavedNewsFragmentToNewsDetailFragment2(
                articleList[position]
            ))
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshAdapter(newList : List<Article>){
        articleList.clear()
        articleList.addAll(newList)
        this.notifyDataSetChanged()
    }

}