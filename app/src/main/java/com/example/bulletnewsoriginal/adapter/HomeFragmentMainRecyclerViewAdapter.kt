package com.example.bulletnewsoriginal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.model.NewsDataClass
import com.example.bulletnewsoriginal.view.HomeFragmentDirections
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.main_recycler_view_item.view.*
import java.security.AccessControlContext

class HomeFragmentMainRecyclerViewAdapter(
    private var list: ArrayList<NewsDataClass>,
    private val context:  Context ):
    RecyclerView.Adapter<HomeFragmentMainRecyclerViewAdapter.ItemHolder>() {

    class ItemHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_view_item,parent,false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.view.homeFragment_main_recyclerView_titleText.text = list[position].category
        val childRecyclerViewAdapter = HomeFragmentChildRecyclerViewAdapter(list[position],context)
        holder.view.homeFragment_child_recyclerView.apply {
            this.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            this.adapter = childRecyclerViewAdapter
        }

        holder.view.homeFragment_main_recyclerView_readMoreText.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToAllNewsFragment(
                list[position]
            ))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun replaceAdapterWithNewArray(newList: List<NewsDataClass>){
        list.clear()
        list.addAll(newList)
        this.notifyDataSetChanged()
    }

}