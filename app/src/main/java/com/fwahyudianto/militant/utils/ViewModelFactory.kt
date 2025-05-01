package com.fwahyudianto.militant.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fwahyudianto.militant.data.Injection
import com.fwahyudianto.militant.data.repository.FavoriteEventRepository
import com.fwahyudianto.militant.ui.account.AccountViewModel
import com.fwahyudianto.militant.ui.events.FavoriteEventsViewModel

class ViewModelFactory private constructor(
    private val repository: FavoriteEventRepository? = null,
    private val settings: SettingPreferences? = null
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteEventsViewModel::class.java) -> {
                FavoriteEventsViewModel(repository!!) as T
            }

            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(settings!!) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(repository = Injection.provideRepository(context))
            }.also { instance = it }

        fun getInstance(settings: SettingPreferences): ViewModelFactory =
            ViewModelFactory(settings = settings)
    }
}
