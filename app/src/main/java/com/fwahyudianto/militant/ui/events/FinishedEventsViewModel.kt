package com.fwahyudianto.militant.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FinishedEventsViewModel : ViewModel() {
    private val mFinishedEventsText = MutableLiveData<String>().apply {
        value =
            "This feature [FinishedEvents] is currently under development. Stay tuned for updates!"
    }

    val dtFinishedEvents: LiveData<String> = mFinishedEventsText
}