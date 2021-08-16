package com.example.bulletnewsoriginal.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.bulletnewsoriginal.R
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.android.synthetic.main.activity_main.*

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        operateSystemUI()


        super.onViewCreated(view, savedInstanceState)
    }

    private fun operateSystemUI(){
        (activity as AppCompatActivity).activity_appbar.apply {
            this.visibility = View.VISIBLE
            this.elevation = 5f
        }
        (activity as AppCompatActivity).activity_toolbar.title = "Profile"
        (activity as AppCompatActivity).activity_bottom_menu.visibility = View.VISIBLE
        (activity as AppCompatActivity).activity_FAB.hide()

    }

}