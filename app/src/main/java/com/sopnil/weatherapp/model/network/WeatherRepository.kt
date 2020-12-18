package com.sopnil.weatherapp.model.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sopnil.weatherapp.BuildConfig
import com.sopnil.weatherapp.model.WeatherResponse
import com.sopnil.weatherapp.model.local.SharedPreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    /**
     * network call to get weather data via API
     */
    fun getWeatherDataFor(cityName : String) : LiveData<WeatherResponse>{
        val weatherResponseLiveData = MutableLiveData<WeatherResponse>()
        val request = ServiceBuilder.buildService(WeatherService::class.java)
        val call = request.getCurrentWeatherData(cityName , BuildConfig.APP_ID)
        call.enqueue(object  : Callback<WeatherResponse>{
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful){
                    weatherResponseLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Need to handel error
                Log.d("Error" , t.localizedMessage)
            }
        })
        return weatherResponseLiveData
    }

    fun getCitiesFromSharedPref(context: Context): MutableSet<String>?{
        return SharedPreferenceManager.getInstance(context).getCityList()
    }

    fun setCitiesToSharedPref(context: Context, citySet : MutableSet<String>?){
        SharedPreferenceManager.getInstance(context).setCityList(citySet)
    }
}