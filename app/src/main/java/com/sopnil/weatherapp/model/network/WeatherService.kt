package com.sopnil.weatherapp.model.network

import com.sopnil.weatherapp.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("q") cityName:String , @Query("appid") appId: String) : Call<WeatherResponse>
}