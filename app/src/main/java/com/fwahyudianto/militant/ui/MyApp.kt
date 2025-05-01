package com.fwahyudianto.militant.ui

//  Import Library
import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.fwahyudianto.militant.utils.SettingPreferences
import com.fwahyudianto.militant.utils.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        CoroutineScope(Dispatchers.Default).launch {
            pref.getThemeSetting().collect { isDark ->
                AppCompatDelegate.setDefaultNightMode(
                    if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )

                Log.d("Militan-MyApp", "Theme Active: ${if (isDark) "Dark Mode" else "Light Mode"}")
            }
        }
    }
}