package com.sopnil.weatherapp.model.local

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager private constructor() {

    companion object {
        private val sharePref = SharedPreferenceManager()
        private lateinit var sharedPreferences: SharedPreferences
        private val CITY_LIST = "city_list"
        fun getInstance(context: Context): SharedPreferenceManager {
            if (!::sharedPreferences.isInitialized) {
                synchronized(SharedPreferenceManager::class.java) {
                        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                }
            }
            return sharePref
        }
    }

    /**
     * get list of cities from shared preference
     * if initially set is empty , we will return empty set
     */
    fun getCityList() : MutableSet<String>? {
        return sharedPreferences.getStringSet(
            CITY_LIST, setOf<String>())?.toMutableSet()
    }

    /**
     * save cities which are bookmarked into shared preference
     */
    fun setCityList(cityList: MutableSet<String>?) {
        sharedPreferences.edit()
            .putStringSet(CITY_LIST, cityList?.toSet())
            .apply()
    }
}

