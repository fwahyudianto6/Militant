package com.fwahyudianto.militant.ui.events

//  Import Library
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwahyudianto.militant.data.datasource.local.entity.FavoriteEvent
import com.fwahyudianto.militant.data.repository.FavoriteEventRepository
import com.fwahyudianto.militant.data.response.DetailResponse
import com.fwahyudianto.militant.data.services.ApiConfig
import com.fwahyudianto.militant.utils.Result
import kotlinx.coroutines.launch

class FavoriteEventsViewModel(private val repoFavorite: FavoriteEventRepository) : ViewModel() {
    private val _result = MutableLiveData<Result<DetailResponse>>()
    //  val result: LiveData<Result<DetailResponse>> = _result

    fun getDetailFavorite(eventName: String) {
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

    fun insertFavEvent(favorite: FavoriteEvent) {
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