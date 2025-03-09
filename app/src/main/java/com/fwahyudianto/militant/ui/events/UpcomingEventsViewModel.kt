package com.fwahyudianto.militant.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpcomingEventsViewModel : ViewModel() {
    private val mUpcomingEventsText = MutableLiveData<String>().apply {
        value =
            "This feature [UpcomingEvents] is currently under development. Stay tuned for updates!"
    }

    val dtUpcomingEvents: LiveData<String> = mUpcomingEventsText
}