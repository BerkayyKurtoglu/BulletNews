package com.example.bulletnewsoriginal.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.ChangeBounds
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.databinding.FragmentNewsDetailBinding
import com.example.bulletnewsoriginal.model.Article
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news_detail.*
import java.lang.Appendable

class NewsDetailFragment : Fragment() {

    private lateinit var dataBinding: FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentNewsDetailBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment with data binding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).apply {
            this.activity_FAB.hide()
            this.activity_bottom_menu.visibility = View.GONE
        }
        arguments?.let {
            val article = NewsDetailFragmentArgs.fromBundle(it).article
            article.source.name?.let { thing->
                (activity as AppCompatActivity).activity_toolbar.title = thing
            }
            details_fragment_go_website_button.setOnClickListener {goToWebsite(article.url)}
            dataBinding.news = article
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun goToWebsite(url : String?){
        url?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }

}