package com.fwahyudianto.militant.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {
    private val mNewsText = MutableLiveData<String>().apply {
        value = "This feature [News] is currently under development. Stay tuned for updates!"
    }

    val dtNews: LiveData<String> = mNewsText
}