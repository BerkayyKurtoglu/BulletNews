package com.example.bulletnewsoriginal.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.adapter.AllNewsFragmentRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_all_news.*

class AllNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).activity_FAB.hide()

        allNewsFragment_recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        arguments?.let {
            val allNews = AllNewsFragmentArgs.fromBundle(it).allNews
            val adapter = AllNewsFragmentRecyclerAdapter(allNews)
            allNewsFragment_recyclerView.adapter = adapter
        }

        super.onViewCreated(view, savedInstanceState)
    }

}