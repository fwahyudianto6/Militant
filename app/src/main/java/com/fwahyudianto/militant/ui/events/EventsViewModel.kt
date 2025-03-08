package com.fwahyudianto.militant.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventsViewModel : ViewModel() {
    private val mEventsText = MutableLiveData<String>().apply {
        value = "This feature is currently under development. Stay tuned for updates!"
    }

    val dtEvents: LiveData<String> = mEventsText
}