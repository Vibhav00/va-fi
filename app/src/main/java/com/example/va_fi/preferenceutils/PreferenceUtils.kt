package com.example.va_fi.preferenceutils

import android.content.Context
import android.content.SharedPreferences
import com.example.va_fi.constants.Constants

class PreferenceUtils {


    companion object {
        private lateinit var mSharedPreferences: SharedPreferences

        /**
         *  implementing shared preferences
         **/
        fun getSharedPreferences(context: Context): Companion {
            mSharedPreferences =
                context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

            return this

        }


        /** funtion to get string **/
        fun getLastUrl(): String {
            return mSharedPreferences.getString("url", "initial value").toString()
        }

        fun setLastUrl(name: String): Boolean {
            mSharedPreferences.edit().putString("url", name).apply()
            return true
        }



        /** funtion to get string **/
        fun getLastActive(): Long {
            return mSharedPreferences.getLong("time", 0)
        }

        fun setLastActive(time:Long): Boolean {
            mSharedPreferences.edit().putLong("time", time).apply()
            return true
        }




    }
}