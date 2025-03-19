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

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Militan Events"
    }
    private val mEventCollection = MutableLiveData<List<ListEventsItem>>()
    private val mIsLoading = MutableLiveData<Boolean>()

    val text: LiveData<String> = _text
    val mEvent: LiveData<List<ListEventsItem>> = mEventCollection
    val isLoading: LiveData<Boolean> = mIsLoading

    init {
        getFinishedEvents()
    }

    private fun getFinishedEvents() {
        mIsLoading.value = true

        val mUserService = ApiConfig.getApiService().getListByParam(0, null, 5)
        mUserService.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                mIsLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        Log.d(TAG, "onSuccess: ${responseBody.listEvents}")
                        setData(responseBody.listEvents)
                    }
                } else {
                    Log.e(TAG, "onFailed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                mIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setData(itemEvents: List<ListEventsItem>) {
        mEventCollection.value = itemEvents
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}