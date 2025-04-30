package com.fwahyudianto.militant.data.repository

//  Import Library
import androidx.lifecycle.LiveData
import com.fwahyudianto.militant.data.datasource.local.entity.FavoriteEvent
import com.fwahyudianto.militant.data.datasource.local.room.IFavoriteEvent

class FavoriteEventRepository private constructor(private val iFavorite: IFavoriteEvent) {
    fun getList(): LiveData<List<FavoriteEvent>> = iFavorite.getEvents()

    fun add(addEvent: FavoriteEvent) {
        iFavorite.insertFavorite(addEvent)
    }

    fun update(editEvent: FavoriteEvent) {
        iFavorite.updateFavorite(editEvent)
    }

    fun delete(deleteEvent: FavoriteEvent) {
        iFavorite.deleteFavorite(deleteEvent)
    }

    fun deleteById(eventId: Int) {
        iFavorite.deleteFavoriteById(eventId)
    }

    fun isEventFavorited(eventId: Int): LiveData<List<FavoriteEvent>> =
        iFavorite.getFavoriteById(eventId)

    companion object {
        @Volatile
        private var instance: FavoriteEventRepository? = null
        fun getInstance(
            favoriteDao: IFavoriteEvent
        ): FavoriteEventRepository = instance ?: synchronized(this) {
            instance ?: FavoriteEventRepository(favoriteDao)
        }.also { instance = it }
    }
}