package com.jhlee.coronabusan

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SharePreferences : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getAlert(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue).toInt()
    }
    fun setAlert(key: String, str: Int) {
        prefs.edit().putInt(key, str).apply()
    }
}
