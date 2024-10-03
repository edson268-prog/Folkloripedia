package com.edsondev26.folkloripedia.utils

import android.content.Context
import java.util.Locale

object LanguageUtils {
    private const val PREF_NAME = "LanguagePref"
    private const val KEY_SELECTED_LANGUAGE = "SelectedLanguage"

    fun getSavedLanguage(context: Context): String {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(KEY_SELECTED_LANGUAGE, Locale.getDefault().language)
            ?: Locale.getDefault().language
    }

    fun setLanguage(context: Context, languageCode: String) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(KEY_SELECTED_LANGUAGE, languageCode)
            apply()
        }
    }
}