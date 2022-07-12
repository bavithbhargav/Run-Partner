package com.example.runpartner.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.runpartner.R
import com.example.runpartner.databinding.ActivityMainBinding
import com.example.runpartner.db.RunDao
import com.example.runpartner.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        navigateToTrackingFragmentIfNeeded(intent)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigation,navController)

//        bottomNavigation.setOnNavigationItemReselectedListener { /*NO_OP*/ }

        navHostFragment.findNavController()
            .addOnDestinationChangedListener{ _,destination,_ ->
                when(destination.id){
                    R.id.settingsFragment, R.id.runFragment,R.id.statisticsFragment ->
                        bottomNavigation.visibility = View.VISIBLE
                    else ->
                        bottomNavigation.visibility = View.GONE
                }

            }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?){
        if(intent?.action == ACTION_SHOW_TRACKING_FRAGMENT){
            navHostFragment.findNavController().navigate(R.id.action_global_trackingFragment)
        }
    }
}