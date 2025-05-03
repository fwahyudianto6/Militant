package com.fwahyudianto.militant.ui.home

//  Import Library
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fwahyudianto.militant.data.response.EventResponse
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.data.services.ApiConfig
import retrofit2.Call
import retrofit2.Response
import java.util.Locale

/**
 *  This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 *  https://www.fwahyudianto.id
 *  ® Wahyudianto, Fajar
 *  Email 	: me@fwahyudianto.id
 */

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Militan Events"
    }
    private val mUpcomingEventsColl = MutableLiveData<List<ListEventsItem>>()
    private val mFinishedEventsColl = MutableLiveData<List<ListEventsItem>>()
    private val mSearchEventsColl = MutableLiveData<List<ListEventsItem>>()

    private val mIsLoadingUpcoming = MutableLiveData<Boolean>()
    private val mIsLoadingFinished = MutableLiveData<Boolean>()
    private val mErrorMessage = MutableLiveData<String>()

    private var filtered = mutableListOf<ListEventsItem>()
    val text: LiveData<String> = _text
    val mUpcomingEvents: LiveData<List<ListEventsItem>> = mUpcomingEventsColl
    val mFinishedEvents: LiveData<List<ListEventsItem>> = mFinishedEventsColl
    val mSearchEvents: LiveData<List<ListEventsItem>> = mSearchEventsColl

    val isLoadingUpcoming: LiveData<Boolean> = mIsLoadingUpcoming
    val isLoadingFinished: LiveData<Boolean> = mIsLoadingFinished
    val errorMessage: LiveData<String> = mErrorMessage

    init {
        getUpcomingEvents()
        getFinishedEvents()
    }

    private fun getFinishedEvents() {
        mIsLoadingFinished.value = true

        val oFinishedEvents = ApiConfig.getApiService().getListByParam(0)
        oFinishedEvents.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                mIsLoadingFinished.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Log.d(TAG, "onSuccess-FinishedEvents: ${responseBody.listEvents}")
                        setData(responseBody.listEvents, mFinishedEventsColl)
                    }
                } else {
                    Log.e(TAG, "Militan-FinishedEvents-onFailed-: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                mIsLoadingFinished.value = false
                handleFailure("Militan-FinishedEvents-onFailure", t)
            }
        })
    }

    private fun getUpcomingEvents() {
        mIsLoadingUpcoming.value = true

        val oUpcomingEvents = ApiConfig.getApiService().getListByParam(1)
        oUpcomingEvents.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                mIsLoadingUpcoming.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setData(responseBody.listEvents, mUpcomingEventsColl)
                    }
                } else {
                    Log.e(TAG, "Militan-UpcomingEvents-onFailed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                mIsLoadingUpcoming.value = false
                handleFailure("Militan-UpcomingEvents-onFailure", t)
            }
        })
    }

    private fun searchEvents(query: String) {
        val oSearchingEvents = ApiConfig.getApiService().getListByParam(0, query, 5)
        oSearchingEvents.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                mIsLoadingUpcoming.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setData(responseBody.listEvents, mSearchEventsColl)
                    }
                } else {
                    Log.e(TAG, "Militan-searchEvents-onFailed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                mIsLoadingUpcoming.value = false
                handleFailure("Militan-searchEvents-onFailure", t)
            }
        })
    }

    private fun search() {
        filtered.clear()
        val filteredtext = newText.lowercase(Locale.getDefault())
        if (filteredtext.isNotEmpty()) {
            searchEvents(filteredtext)
        }
    }

    var newText: String = ""
        set(value) {
            field = value
            search()
        }

    private fun setData(
        itemEvents: List<ListEventsItem>,
        itemCollections: MutableLiveData<List<ListEventsItem>>
    ) {
        itemCollections.value = itemEvents
    }

    private fun handleFailure(source: String, t: Throwable) {
        val message = when (t) {
            is java.net.SocketTimeoutException -> "Timeout!"
            is java.net.UnknownHostException -> "No internet!"
            is java.io.IOException -> "Network error!"
            else -> "Unexpected error: ${t.localizedMessage}"
        }

        Log.e(TAG, "$source error: $message")
        mErrorMessage.value = message
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}