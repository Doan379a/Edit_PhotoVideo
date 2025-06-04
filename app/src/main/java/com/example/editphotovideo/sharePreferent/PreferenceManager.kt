package com.example.editphotovideo.sharePreferent

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "CallerAnnounce"
        private const val SEEKBAR_LOW = "SEEKBAR_LOW"

    }

    fun saveSeekBarLow(value: Int) {
        sharedPref.edit().putInt(SEEKBAR_LOW, value).apply()
    }

    fun getSeekBarLow(): Int {
        return sharedPref.getInt(SEEKBAR_LOW, 0)
    }


}

