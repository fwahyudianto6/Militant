package com.fwahyudianto.militant.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamsViewModel : ViewModel() {
    private val mTeamsText = MutableLiveData<String>().apply {
        value = "This feature [Teams] is currently under development. Stay tuned for updates!"
    }

    val dtTeams: LiveData<String> = mTeamsText
}