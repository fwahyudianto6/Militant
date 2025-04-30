package com.fwahyudianto.militant.ui.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fwahyudianto.militant.data.response.EventResponse
import com.fwahyudianto.militant.data.response.ListEventsItem
import com.fwahyudianto.militant.data.services.ApiConfig
import retrofit2.Call
import retrofit2.Response

//  Import Library

class EventDetailViewModel : ViewModel() {
    private val mDetailEventsColl = MutableLiveData<List<ListEventsItem>>()
    private val mIsLoadingDetail = MutableLiveData<Boolean>()
    private val mErrorMessage = MutableLiveData<String>()

    val mDetailEvents: LiveData<List<ListEventsItem>> = mDetailEventsColl
    val isLoadingDetail: LiveData<Boolean> = mIsLoadingDetail
    val errorMessage: LiveData<String> = mErrorMessage

    fun getDetailEvents(eventId: String) {
        mIsLoadingDetail.value = true

        val oDetailEvents = ApiConfig.getApiService().getEvent(eventId)
        oDetailEvents.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                mIsLoadingDetail.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        Log.d(TAG, "onSuccess-DetailEvents: ${responseBody.listEvents}")
                        setData(responseBody.listEvents, mDetailEventsColl)
                    }
                } else {
                    Log.e(TAG, "onFailed-DetailEvents: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                mIsLoadingDetail.value = false
                handleFailure(t)
            }
        })
    }

    private fun setData(
        itemEvents: List<ListEventsItem>,
        itemCollections: MutableLiveData<List<ListEventsItem>>
    ) {
        itemCollections.value = itemEvents
    }

    private fun handleFailure(t: Throwable) {
        val source = "DetailEvents"
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
        private const val TAG = "DetailEventsViewModel"
    }
}