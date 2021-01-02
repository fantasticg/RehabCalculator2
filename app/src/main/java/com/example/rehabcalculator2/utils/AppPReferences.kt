package com.example.rehabcalculator2.utils

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context : Context) {
    val PREFS_FILENAME = "prefs"
    val PREFS_KEY_SCHEDULES_ID = "schedules_id"
    val prefs : SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var pref_databaseId : Long
        get() = prefs.getLong(PREFS_KEY_SCHEDULES_ID, 1) // 왜?? defValue 0일때 1회 FC
        set(value) = prefs.edit().putLong(PREFS_KEY_SCHEDULES_ID, value).apply()

}