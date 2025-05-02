package com.fwahyudianto.militant.data.repository

//  Import Library
import androidx.lifecycle.LiveData
import com.fwahyudianto.militant.data.datasource.local.entity.FavoriteEvent
import com.fwahyudianto.militant.data.datasource.local.room.IFavoriteEvent

class FavoriteEventRepository private constructor(private val iFavorite: IFavoriteEvent) {
    fun getList(): LiveData<List<FavoriteEvent>> = iFavorite.getEvents()

    suspend fun add(addEvent: FavoriteEvent) {
        iFavorite.insertFavorite(addEvent)
    }

    @Suppress("Unused")
    suspend fun update(editEvent: FavoriteEvent) {
        iFavorite.updateFavorite(editEvent)
    }

    @Suppress("Unused")
    suspend fun delete(deleteEvent: FavoriteEvent) {
        iFavorite.deleteFavorite(deleteEvent)
    }

    suspend fun deleteById(eventId: Int) {
        iFavorite.deleteFavoriteById(eventId)
    }

    fun isEventFavorited(eventId: Int): LiveData<List<FavoriteEvent>> =
        iFavorite.getFavoriteById(eventId)

    companion object {
        @Volatile
        private var instance: FavoriteEventRepository? = null
        fun getInstance(
            iFav: IFavoriteEvent
        ): FavoriteEventRepository = instance ?: synchronized(this) {
            instance ?: FavoriteEventRepository(iFav)
        }.also { instance = it }
    }
}