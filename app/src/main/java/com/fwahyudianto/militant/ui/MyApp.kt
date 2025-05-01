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
import kotlinx.coroutines.withContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        CoroutineScope(Dispatchers.Default).launch {
            pref.getThemeSetting().collect { isDark ->
                withContext(Dispatchers.Main) {
                    try {
                        AppCompatDelegate.setDefaultNightMode(
                            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                            else AppCompatDelegate.MODE_NIGHT_NO
                        )
                        Log.d("Militan-MyApp", "Theme set to: ${if (isDark) "Dark" else "Light"}")
                    } catch (e: Exception) {
                        Log.e("Militan-MyApp", "Failed to set theme: ${e.message}")
                    }
                }
            }
        }
    }
}