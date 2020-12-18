package com.sopnil.weatherapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sopnil.weatherapp.R
import com.sopnil.weatherapp.model.WeatherResponse
import com.sopnil.weatherapp.viewmodel.WeatherDetailViewModel
import kotlinx.android.synthetic.main.weather_details_fragment.*

class WeatherDetailFragment : Fragment() {

    private var mView : View? = null
    private lateinit var mWeatherDetailViewModel: WeatherDetailViewModel
    companion object{
        val ARGS_CITY_NAME : String = "args_city_name"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater.inflate(R.layout.weather_details_fragment, container, false)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCityDetails()
        loadWeatherDetails()
    }

    /**
     * get arguments from bundel
     * for which user is interested to see weather
     * showing city name on top
     */
    private fun setCityDetails(){
        txtCityNameWeatherDetails.text= arguments?.getString(ARGS_CITY_NAME)
    }

    /**
     * give a call to load weather from network
     */
    private fun loadWeatherDetails(){
        mWeatherDetailViewModel = ViewModelProvider(this).get(WeatherDetailViewModel::class.java)
        mWeatherDetailViewModel.getWeatherData(txtCityNameWeatherDetails.text.toString())
            .observe(viewLifecycleOwner ,
            Observer { weatherResponse ->
               showWeatherDetails(weatherResponse)
        })
    }

    /**
     * set details from response to view
     */
    private fun showWeatherDetails(weatherResponse: WeatherResponse){
        txtTemperatureWeatherDetails.text =
            String.format("%s  %s" , resources.getString(R.string.temperature),
                weatherResponse.main?.temperature.toString())

        txtHumidityWeatherDetails.text =
            String.format("%s  %s" , resources.getString(R.string.humidity),
                weatherResponse.main?.humidity.toString())

        txtWindWeatherDetails.text =
            String.format("%s  %s %s" , resources.getString(R.string.wind),
                weatherResponse.wind?.speed, resources.getString(R.string.speed_measure))
    }
}