package com.example.h_mal.movielisttest.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * Shared preferences to save & load last timestamp
 */
private const val PAGE_NUMBER = "page_number"
class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun savePageNumber() {
        var pages = getPageNumber()
        pages++
        preference.edit().putInt(
            PAGE_NUMBER,
            pages
        ).apply()
    }

    fun getPageNumber(): Int {
        return preference.getInt(PAGE_NUMBER, 1)
    }

}