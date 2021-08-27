package com.example.bulletnewsoriginal.view

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.adapter.ItemTouchHelperCallBack
import com.example.bulletnewsoriginal.adapter.SavedNewsRecyclerViewAdapter
import com.example.bulletnewsoriginal.viewModel.SavedNewsFragmentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment : Fragment() {

    private lateinit var savedNewsFragmentViewModel: SavedNewsFragmentViewModel
    private var recyclerAdapter = SavedNewsRecyclerViewAdapter(ArrayList())
    private lateinit var itemTouchHelperCallBack : ItemTouchHelperCallBack
    private lateinit var itemTouchHelper : ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleSystemUI()
        savedNewsFragmentViewModel = ViewModelProviders.of(this).get(SavedNewsFragmentViewModel::class.java)
        savedNewsFragmentViewModel.getSavedArticles()

        itemTouchHelperCallBack = ItemTouchHelperCallBack(ArrayList(),recyclerAdapter,requireContext())
        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)

        savedNews_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemTouchHelper.attachToRecyclerView(savedNews_recyclerView)


        observeViewModel()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun observeViewModel(){

        savedNewsFragmentViewModel.articlesLiveData.observe(viewLifecycleOwner){
            it?.let {
                recyclerAdapter.refreshAdapter(it)
                savedNews_recyclerView.adapter = recyclerAdapter
                itemTouchHelperCallBack.refreshList(it)
            }
        }

        savedNewsFragmentViewModel.progressLiveData.observe(viewLifecycleOwner){
            if (it){
                savedNews_progress.visibility = View.VISIBLE
                savedNews_recyclerView.visibility = View.INVISIBLE
            }else{
                savedNews_progress.visibility = View.INVISIBLE
                savedNews_recyclerView.visibility = View.VISIBLE
            }
        }

    }

    private fun handleSystemUI(){
        (activity as AppCompatActivity).activity_appbar.apply {
            this.visibility = View.VISIBLE
            this.elevation = 0F
        }
        (activity as AppCompatActivity).activity_toolbar.apply {
            this.title = "My Saved News"
            this.visibility = View.VISIBLE
        }

        (activity as AppCompatActivity).activity_bottom_menu.visibility = View.GONE
        (activity as AppCompatActivity).activity_FAB.hide()

    }

}