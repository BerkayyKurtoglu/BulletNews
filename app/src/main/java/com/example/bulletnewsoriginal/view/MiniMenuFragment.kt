package com.example.bulletnewsoriginal.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.databinding.FragmentMiniMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_mini_menu.*

class MiniMenuFragment : BottomSheetDialogFragment() {

    private lateinit var dataBinding : FragmentMiniMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentMiniMenuBinding.inflate(inflater,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            val url = MiniMenuFragmentArgs.fromBundle(it).url
            dataBinding.url = url
            miniMenuFragment_urlText.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            miniMenuFragment_share_button.setOnClickListener {shareUrl(url)}
        }


        super.onViewCreated(view, savedInstanceState)
    }

    private fun shareUrl(url : String){
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT,"You should take a look at that news : \n $url")
            this.type= "text/plain"
        }
        startActivity(shareIntent)
    }

}