package com.example.bulletnewsoriginal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.databinding.AllFragmentRecyclerviewItemBinding
import com.example.bulletnewsoriginal.databinding.FragmentAllNewsBinding
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.view.AllNewsFragmentDirections

class AllNewsFragmentRecyclerAdapter(
    private val newsDataClass: NewsDataClass
) : RecyclerView.Adapter<AllNewsFragmentRecyclerAdapter.ItemHolder>() {

    class ItemHolder(val dataBinding : AllFragmentRecyclerviewItemBinding) : RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = AllFragmentRecyclerviewItemBinding.inflate(inflater,parent,false)
        return ItemHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val article = newsDataClass.articles[position]
        holder.dataBinding.article = article

        holder.dataBinding.root.setOnClickListener {
            Navigation.findNavController(it).navigate(AllNewsFragmentDirections.actionAllNewsFragmentToNewsDetailFragment2(
                article
            ))
        }

    }

    override fun getItemCount(): Int {
        return newsDataClass.articles.size
    }
}