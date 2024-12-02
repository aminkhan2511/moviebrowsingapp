package com.example.moviebrowsingapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.moviebrowsingapp.R
import com.example.moviebrowsingapp.data.repository.MainRepository
import com.example.moviebrowsingapp.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "What do you want to watch ?"

        val mainRepository = MainRepository()
        val viewModelProviderFactory = MainViewModelProviderFactory(mainRepository, application)
        mainViewModel = ViewModelProvider(this, viewModelProviderFactory)[MainViewModel::class.java]


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit() // Do not add to the back stack for the initial fragment
        }

    }



    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed() // Exit the activity
        }
    }

}