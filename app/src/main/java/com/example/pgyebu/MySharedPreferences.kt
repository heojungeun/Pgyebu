package com.example.pgyebu

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name",Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }

    fun setInt(key: String, num: Int) {
        prefs.edit().putInt(key, num).apply()
    }

    fun getLong(key: String, defValue: Long): Long {
        return prefs.getLong(key, defValue)
    }

    fun setLong(key: String, num: Long) {
        prefs.edit().putLong(key, num).apply()
    }

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

}