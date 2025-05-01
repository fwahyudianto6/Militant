package com.fwahyudianto.militant.ui.events

//  Import Library
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fwahyudianto.militant.data.response.DetailResponse
import com.fwahyudianto.militant.data.response.Event
import com.fwahyudianto.militant.data.services.ApiConfig
import retrofit2.Call
import retrofit2.Response

class EventDetailViewModel() : ViewModel() {
    private val mDetailEventsColl = MutableLiveData<Event>()
    private val mIsLoadingDetail = MutableLiveData<Boolean>()
    private val mErrorMessage = MutableLiveData<String>()

    val mDetailEvents: LiveData<Event> = mDetailEventsColl
    val isLoadingDetail: LiveData<Boolean> = mIsLoadingDetail
    val errorMessage: LiveData<String> = mErrorMessage

//    init {
//        val eventId = savedStateHandle.get<String>(KEY_EVENT_ID)
//        eventId?.let { getDetailEvents(it) }
//    }

    fun getDetailEvents(eventId: String) {
        mIsLoadingDetail.value = true

        val oDetailEvents = ApiConfig.getApiService().getEvent(eventId)
        oDetailEvents.enqueue(object : retrofit2.Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                mIsLoadingDetail.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        Log.d(TAG, "onSuccess-DetailEvents: ${responseBody.event}")
                        responseBody.event?.let { setData(it, mDetailEventsColl) }
                    } else {
                        mErrorMessage.value = "Response body is null!"
                        return
                    }
                } else {
                    Log.e(TAG, "onFailed-DetailEvents: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                mIsLoadingDetail.value = false
                handleFailure(t)
            }
        })
    }

    private fun setData(
        itemEvents: Event,
        itemCollections: MutableLiveData<Event>
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
        const val KEY_EVENT_ID = "eventId"
    }
}