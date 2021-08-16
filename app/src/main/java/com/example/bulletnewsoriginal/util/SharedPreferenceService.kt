package com.example.bulletnewsoriginal.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class SharedPreferenceService {

    companion object{

        private var sharedPreferences : SharedPreferences? = null
        @Volatile private var service : SharedPreferenceService? = null

        operator fun invoke(context: Context) : SharedPreferenceService = service ?: synchronized(this){
            service ?: createSharedPreference(context).also {
                service = it
            }
        }

        private fun createSharedPreference(context : Context) : SharedPreferenceService{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferenceService()
        }
    }


    fun editCheckState(name : String,value : Boolean){
        sharedPreferences?.edit(commit = true){
            putBoolean(name,value).apply()
        }
    }

    fun controlCheckState(name : String) = sharedPreferences?.getBoolean(name,false)

}