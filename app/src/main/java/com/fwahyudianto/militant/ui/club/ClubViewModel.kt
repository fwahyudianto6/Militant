package com.fwahyudianto.militant.ui.club

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClubViewModel : ViewModel() {
    private val mClubText = MutableLiveData<String>().apply {
        value = "This feature is currently under development. Stay tuned for updates!"
    }

    val dtClub: LiveData<String> = mClubText
}