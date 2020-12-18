package com.sopnil.weatherapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sopnil.weatherapp.view.fragment.HomeFragment
import com.sopnil.weatherapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceGivenFragment(HomeFragment())
    }

    /**
     * This is common method in Main activity
     * So that all fragments can be replaced in in framelayout
     */
     fun replaceGivenFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .addToBackStack(fragment.javaClass.name)
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    /**
     * when there will be no fragment in backstack
     * finish the MainActivity
     * as there will be no UI for user
     */
    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0){
            finish()
        }
    }
}