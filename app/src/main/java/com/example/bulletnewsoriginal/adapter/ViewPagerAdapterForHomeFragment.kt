package com.example.bulletnewsoriginal.adapter

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
import kotlinx.android.synthetic.main.view_pager_item_home_fragment.view.*

class ViewPagerAdapterForHomeFragment(
    private val topHeadlinesNews : NewsDataClass
)
    : RecyclerView.Adapter<ViewPagerAdapterForHomeFragment.ItemHolder>() {
    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_pager_item_home_fragment, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val article = topHeadlinesNews.articles[position]
        article.urlToImage?.let {
            holder.view.viewPager_CardView_NewsImageView.uploadImageFromUrl(
                it,
                createPlaceHolder(holder.view.context)
            )
        }
        article.title?.let {
            holder.view.viewPager_CardView_TitleTextView.text = it
        }
        article.source?.let { it ->
            it.name?.let { name ->
                holder.view.viewPager_CardView_SourceTextView.text = name
            }
        }
        holder.view.viewPager_CardView.setOnClickListener {
            val extras =
                FragmentNavigatorExtras(holder.view.viewPager_CardView_NewsImageView to "shared_element")
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeFragmentToNewsDetailFragment2(article),
                extras
            )
        }
    }

    override fun getItemCount(): Int {
        return topHeadlinesNews.articles.size
    }


}