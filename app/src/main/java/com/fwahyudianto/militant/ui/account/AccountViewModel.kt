package com.fwahyudianto.militant.ui.account

//  Import Library
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fwahyudianto.militant.utils.SettingPreferences
import kotlinx.coroutines.launch

class AccountViewModel(private val settings: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return settings.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settings.saveThemeSetting(isDarkModeActive)
        }
    }
}