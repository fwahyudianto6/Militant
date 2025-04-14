package com.fwahyudianto.militant.ui.events

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

/**
 *  This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 *  https://www.fwahyudianto.id
 *  ® Wahyudianto, Fajar
 *  Email 	: me@fwahyudianto.id
 */

class UpcomingEventsViewModel : ViewModel() {
    private val mUpcomingEventsColl = MutableLiveData<List<ListEventsItem>>()
    private val mIsLoadingUpcoming = MutableLiveData<Boolean>()
    private val mErrorMessage = MutableLiveData<String>()

    val mUpcomingEvents: LiveData<List<ListEventsItem>> = mUpcomingEventsColl
    val isLoadingUpcoming: LiveData<Boolean> = mIsLoadingUpcoming
    val errorMessage: LiveData<String> = mErrorMessage

    init {
        getUpcomingEvents()
    }

    private fun getUpcomingEvents() {
        mIsLoadingUpcoming.value = true

        val oUpcomingEvents = ApiConfig.getApiService().getListByParam(1, null, 5)
        oUpcomingEvents.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                mIsLoadingUpcoming.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        Log.d(TAG, "onSuccess-UpcomingEvents: ${responseBody.listEvents}")
                        setData(responseBody.listEvents, mUpcomingEventsColl)
                    }
                } else {
                    Log.e(TAG, "onFailed-UpcomingEvents: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                mIsLoadingUpcoming.value = false
                handleFailure("UpcomingEvents", t)
            }
        })
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
        private const val TAG = "UpcomingEventsViewModel"
    }
}