package com.rafael.tvmedia.app.view.activity

import android.animation.AnimatorInflater
import android.animation.StateListAnimator
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.rafael.tvmedia.R
import com.rafael.tvmedia.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    ThemeableActivity {

    private lateinit var navController: NavController

    private lateinit var toolbarBg: Drawable

    private lateinit var elevatedAnimator: StateListAnimator

    private lateinit var unelevatedAnimator: StateListAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        initializeNavigation()
        initializeUi()
    }

    override fun onSupportNavigateUp(): Boolean {
        return (navController.navigateUp() || super.onSupportNavigateUp())
    }

    override fun setTransparentToolbar() {
        with(binding.appbarLayout) {
            setBackgroundColor(Color.TRANSPARENT)
            stateListAnimator = unelevatedAnimator
        }
    }

    override fun resetToolbar() {
        with(binding.appbarLayout) {
            background = toolbarBg
            stateListAnimator = elevatedAnimator
        }
    }

    private fun initializeUi() {

        toolbarBg = binding.appbarLayout.background

        elevatedAnimator = AnimatorInflater
            .loadStateListAnimator(this, R.animator.appbar_state_elevated)

        unelevatedAnimator = AnimatorInflater
            .loadStateListAnimator(this, R.animator.appbar_state_unelevated)
    }

    private fun initializeNavigation() {

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        val configuration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, configuration)
    }
}
