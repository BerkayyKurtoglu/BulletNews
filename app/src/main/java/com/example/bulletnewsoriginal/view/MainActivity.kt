package com.example.bulletnewsoriginal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bulletnewsoriginal.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_toolbar)

        operateBottomNavigation()
    }

    private fun operateBottomNavigation(){
        val fragmentManager = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = fragmentManager.navController
        activity_bottom_menu.setupWithNavController(navController)
    }




}