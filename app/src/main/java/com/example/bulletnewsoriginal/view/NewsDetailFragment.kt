package com.example.bulletnewsoriginal.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.databinding.FragmentNewsDetailBinding
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.viewModel.SaveNewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news_detail.*

class NewsDetailFragment : Fragment() {

    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.rotate_open_anim)}
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.rotate_close_anim)}
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.from_bottom_anim)}
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(context,R.anim.to_bottom_anim)}

    private lateinit var saveNewsViewModel: SaveNewsViewModel

    private lateinit var dataBinding: FragmentNewsDetailBinding
    private var clicked : Boolean = false

    private var backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentNewsDetailBinding.inflate(inflater,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).apply {
            this.activity_FAB.hide()
            this.activity_bottom_menu.visibility = View.GONE
        }
        saveNewsViewModel = ViewModelProviders.of(this).get(SaveNewsViewModel::class.java)
        arguments?.let {
            val article = NewsDetailFragmentArgs.fromBundle(it).article
            article.source.name?.let { thing->
                (activity as AppCompatActivity).activity_toolbar.title = thing
            }
            details_fragment_go_website_button.setOnClickListener {goToWebsite(article.url)}
            dataBinding.news = article
            share_fab.setOnClickListener {
                article.url?.let {
                    handleShareFab(it)
                }
            }
            save_fab.setOnClickListener { saveNews(article)}
        }

        mother_fab.show()
        mother_fab.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backPressedCallback)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun saveNews(article: Article) {
        saveNewsViewModel.saveNewsToRoom(article)
        share_fab.startAnimation(toBottom)
        save_fab.startAnimation(toBottom)
        mother_fab.startAnimation(rotateClose)
        share_fab.visibility = View.INVISIBLE
        save_fab.visibility = View.INVISIBLE
        clicked = !clicked
    }

    /**
     * Adjust the visibility according to the value of clicked
     * @param clicked current value*/
    private fun setVisibility(clicked : Boolean){
        if (!clicked){
            share_fab.visibility = View.VISIBLE
            save_fab.visibility = View.VISIBLE
        }else{
            share_fab.visibility = View.INVISIBLE
            save_fab.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean){
        if (!clicked){
            share_fab.startAnimation(fromBottom)
            save_fab.startAnimation(fromBottom)
            mother_fab.startAnimation(rotateOpen)
        }else{
            share_fab.startAnimation(toBottom)
            save_fab.startAnimation(toBottom)
            mother_fab.startAnimation(rotateClose)
        }
    }

    private fun handleShareFab(url : String){
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT,"You should take a look at that news : \n $url")
            this.type= "text/plain"
        }
        startActivity(shareIntent)
    }

    private fun goToWebsite(url : String?){
        url?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }

}