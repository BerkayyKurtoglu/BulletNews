package com.example.bulletnewsoriginal.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.util.SharedPreferenceService
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var sharedPreferenceService: SharedPreferenceService

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
        sharedPreferenceService = SharedPreferenceService(requireContext())
        controlDarkMode()

        fragmentProfile_savedNewsText.setOnClickListener { savedTextClicked() }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun savedTextClicked() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSavedNewsFragment())
    }


    private fun controlDarkMode(){

        profileFragment_NightMode_Switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                sharedPreferenceService.editCheckState("dark_mode",true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                sharedPreferenceService.editCheckState("dark_mode",false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        profileFragment_NightMode_Switch.isChecked = sharedPreferenceService.controlCheckState("dark_mode") == true

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