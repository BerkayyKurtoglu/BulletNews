package com.example.bulletnewsoriginal.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.bulletnewsoriginal.databinding.FragmentMiniMenuBinding
import com.example.bulletnewsoriginal.model.Article
import com.example.bulletnewsoriginal.viewModel.SaveNewsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_mini_menu.*

class MiniMenuFragment : BottomSheetDialogFragment() {

    private lateinit var dataBinding : FragmentMiniMenuBinding
    private lateinit var saveNewsViewModel: SaveNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentMiniMenuBinding.inflate(inflater,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        saveNewsViewModel = ViewModelProviders.of(this).get(SaveNewsViewModel::class.java)
        arguments?.let {
            val article = MiniMenuFragmentArgs.fromBundle(it).article

            miniMenuFragment_save_button.setOnClickListener { saveArticle(article,it) }

            article.url?.let {url->
                dataBinding.url = url
                miniMenuFragment_urlText.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                miniMenuFragment_share_button.setOnClickListener {shareUrl(url)}
            }
        }
        observeViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun saveArticle(article: Article, v : View){
        saveNewsViewModel.saveNewsToRoom(article)
    }

    private fun shareUrl(url : String){
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT,"You should take a look at that news : \n $url")
            this.type= "text/plain"
        }
        startActivity(shareIntent)
    }

    private fun observeViewModel(){
        saveNewsViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if (it){
                miniMenuFragment_progressBar.visibility = View.VISIBLE
                miniMenuFragment_urlText.visibility = View.INVISIBLE
                miniMenuFragment_share_button.visibility = View.INVISIBLE
                miniMenuFragment_save_button.visibility = View.INVISIBLE
            }else{
                miniMenuFragment_progressBar.visibility = View.INVISIBLE
                miniMenuFragment_urlText.visibility = View.VISIBLE
                miniMenuFragment_share_button.visibility = View.VISIBLE
                miniMenuFragment_save_button.visibility = View.VISIBLE
            }
        }
    }

}