package com.sopnil.weatherapp.view.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sopnil.weatherapp.R
import com.sopnil.weatherapp.view.adapter.AdapterCity
import com.sopnil.weatherapp.view.activity.MainActivity
import com.sopnil.weatherapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private var mView: View? = null
    private lateinit var mHomeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false)
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showBookMarkCities()
        initClickListeners()
    }

    /**
     * initialize click listeners
     */
    private fun initClickListeners(){
        fabAddLocation.setOnClickListener {
            (activity as MainActivity).replaceGivenFragment(MapFragment())
        }
    }

    /**
     * get list of cities from shared preference
     * and set to adapter cities
     */
    private fun showBookMarkCities(){
        mHomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val cityList = mHomeViewModel.getBookmarkedCities()
        val cityAdapter = cityList?.toList()?.let {
            AdapterCity(it)
        }
        mView?.recyclerViewCities?.adapter = cityAdapter
        cityAdapter?.onListItemClick = {city , id ->
            when(id){
                R.id.txt_city_name ->{
                    launchWeatherDetailFragment(city)
                }
                R.id.img_delete_city ->{
                    deleteCityFromBookmark(city)
                }
            }
        }
    }

    /**
     * delete city from sharedPreference
     */
    private fun deleteCityFromBookmark(cityName : String){
        val list = mHomeViewModel.getBookmarkedCities()
        list?.remove(cityName)
        mHomeViewModel.updateBookmarkCities(list)
        showBookMarkCities()
        val snackMessage = String.format("%s %s", cityName , resources.getString(R.string.deleted))
        Snackbar.make(coordinatorLayout, snackMessage, Snackbar.LENGTH_LONG).show()
    }

    /**
     * Launch weather detail fragment
     * to see details of a particular clicked city from
     * recycler view
     */
    private fun launchWeatherDetailFragment(city: String){
        val weatherDetailFragment = WeatherDetailFragment()
        val bundle = Bundle()
        bundle.putString(WeatherDetailFragment.ARGS_CITY_NAME, city)
        weatherDetailFragment.arguments = bundle
        (activity as MainActivity).replaceGivenFragment(weatherDetailFragment)
    }
}