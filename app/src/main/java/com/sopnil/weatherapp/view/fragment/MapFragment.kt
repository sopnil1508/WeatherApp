package com.sopnil.weatherapp.view.fragment

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.sopnil.weatherapp.R
import com.sopnil.weatherapp.model.local.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMapFragment : SupportMapFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadMap()
        initClickListeners()
    }

    /**
     * Initialize views and give a call to load
     * Google map
     */
    private fun loadMap(){
        mMapFragment = childFragmentManager//supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mMapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        setDefaultLocation(googleMap)
    }

    /**
     * set default location when maps get loaded initially
     * We have set Pune location as default for now
     */
    private fun setDefaultLocation(googleMap: GoogleMap?){
        var latlang  = LatLng(18.5204 , 73.8567) // used Pune as default location for now
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlang , 10f))
        googleMap?.setOnCameraIdleListener(GoogleMap.OnCameraIdleListener {
            //get latlng at the center of map , where our default marker is, by calling
            latlang = googleMap.getCameraPosition().target
            if (latlang.latitude != 0.0 && latlang.longitude != 0.0) {
                getCityName(latlang)
            }
        })
    }

    /**
     * Extract City name using geoCoder Api
     * set user selected City to text view
     * if city is null , then clear the text
     * and set hint to move the marker
     */
    private fun getCityName(latLng: LatLng) {
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        val addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        val city: String? = addressList?.get(0)?.locality
        if (city != null){
            txtCityNameMap.text = city
        }else{
            txtCityNameMap.text = ""
            txtCityNameMap.hint = getString(R.string.move_marker)
        }
    }

    /**
     * initialize click listeners
     */
   private fun initClickListeners(){
       btnBookmarkCity?.setOnClickListener {
           val cityName = txtCityNameMap.text.toString()
           if (cityName.isNotEmpty()) {
               val cityList = SharedPreferenceManager.getInstance(
                   requireActivity()
               ).getCityList()
               cityList?.add(cityName)
               SharedPreferenceManager.getInstance(
                   requireActivity()
               ).setCityList(cityList)
               val toastMessage = String.format("%s %s", cityName , resources.getString(
                   R.string.city_added
               ))
               Toast.makeText(requireActivity(),toastMessage , Toast.LENGTH_SHORT).show()
           }else{
               Toast.makeText(requireContext() ,resources.getString(R.string.move_marker_for_accuracy)
                       ,Toast.LENGTH_LONG).show()
           }
       }
   }
}