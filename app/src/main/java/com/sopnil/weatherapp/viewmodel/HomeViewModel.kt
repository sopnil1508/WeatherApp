package com.sopnil.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sopnil.weatherapp.model.network.WeatherRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    fun getBookmarkedCities(): MutableSet<String>?{
        return WeatherRepository().getCitiesFromSharedPref(getApplication())
    }

    fun updateBookmarkCities(cities : MutableSet<String>?){
        WeatherRepository().setCitiesToSharedPref(getApplication() , cities)
    }
}