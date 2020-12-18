package com.sopnil.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sopnil.weatherapp.model.WeatherResponse
import com.sopnil.weatherapp.model.network.WeatherRepository

class WeatherDetailViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * from view model , call is forwarded to weather repository
     * to get data from server
     */
    fun getWeatherData(cityName : String) : LiveData<WeatherResponse>{
        return WeatherRepository().getWeatherDataFor(cityName)
    }
}