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

class FinishedEventsViewModel : ViewModel() {
    private val mFinishedEventsColl = MutableLiveData<List<ListEventsItem>>()
    private val mIsLoadingFinished = MutableLiveData<Boolean>()
    private val mErrorMessage = MutableLiveData<String>()

    val mFinishedEvents: LiveData<List<ListEventsItem>> = mFinishedEventsColl
    val isLoadingFinished: LiveData<Boolean> = mIsLoadingFinished
    val errorMessage: LiveData<String> = mErrorMessage

    init {
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
//                        Log.d(TAG, "onSuccess-FinishedEvents: ${responseBody.listEvents}")
                        setData(responseBody.listEvents, mFinishedEventsColl)
                    }
                } else {
                    Log.e(TAG, "onFailed-FinishedEvents: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                mIsLoadingFinished.value = false
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
        val source = "FinishedEvents"
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
        private const val TAG = "FinishedEventsViewModel"
    }
}