package com.fwahyudianto.militant.data.repository

//  Import Library
import android.app.Application
import androidx.lifecycle.LiveData
import com.fwahyudianto.militant.data.datasource.local.entity.FavoriteEvent
import com.fwahyudianto.militant.data.datasource.local.room.FavoriteEventRoom
import com.fwahyudianto.militant.data.datasource.local.room.IFavoriteEvent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteEventRepository(application: Application) {
    private val m_oFavoriteEvent: IFavoriteEvent
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteEventRoom.getInstance(application)
        m_oFavoriteEvent = db.iFavoriteEvent()
    }

    fun getList(): LiveData<List<FavoriteEvent>> = m_oFavoriteEvent.getEvents()

    fun add(addEvent: FavoriteEvent) {
        executorService.execute { m_oFavoriteEvent.insertFavorite(addEvent) }
    }

    fun update(editEvent: FavoriteEvent) {
        executorService.execute { m_oFavoriteEvent.updateFavorite(editEvent) }
    }

    fun delete(deleteEvent: FavoriteEvent) {
        executorService.execute { m_oFavoriteEvent.deleteFavorite(deleteEvent) }
    }
}