package com.grzegorzgajewski.githubsearch.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import com.grzegorzgajewski.githubsearch.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvRxActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupActionBarWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId ?: 0) {
        android.R.id.home -> {
            navController.navigateUp()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
