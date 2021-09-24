package com.example.bulletnewsoriginal.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.bulletnewsoriginal.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var fragmentManager: NavHostFragment
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fragmentManager = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = fragmentManager.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.profileFragment,R.id.homeFragment,R.id.searchFragment))
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
        activity_bottom_menu.setupWithNavController(navController)

    }

    /*override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination?.id){
            finish()
        }else super.onBackPressed()
    }*/

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

}