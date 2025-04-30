package com.fwahyudianto.militant.ui.events

//  Import Library
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwahyudianto.militant.data.datasource.local.entity.FavoriteEvent
import com.fwahyudianto.militant.data.repository.FavoriteEventRepository
import com.fwahyudianto.militant.data.response.EventResponse
import com.fwahyudianto.militant.data.services.ApiConfig
import com.fwahyudianto.militant.utils.Result
import kotlinx.coroutines.launch

class FavoriteEventsViewModel(private val repoFavorite: FavoriteEventRepository) : ViewModel() {
    private val _result = MutableLiveData<Result<EventResponse>>()
    val result: LiveData<Result<EventResponse>> = _result

    fun getDetailFavoriteUser(eventName: String) {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = ApiConfig.getApiService().getFavEventByName(eventName)
                _result.value = Result.Success(response)
            } catch (e: Exception) {
                _result.value = Result.Error(e.message.toString())
            }
        }
    }

    fun getFavEvents(): LiveData<List<FavoriteEvent>> = repoFavorite.getList()

    fun insertUser(favorite: FavoriteEvent) {
        viewModelScope.launch {
            repoFavorite.add(favorite)
        }
    }

    fun isFavorited(eventId: Int): LiveData<List<FavoriteEvent>> =
        repoFavorite.isEventFavorited(eventId)

    fun deleteByEventId(eventId: Int) {
        viewModelScope.launch {
            repoFavorite.deleteById(eventId)
        }
    }
}