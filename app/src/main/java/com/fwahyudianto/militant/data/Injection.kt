package com.fwahyudianto.militant.data

import android.content.Context
import com.fwahyudianto.militant.data.datasource.local.room.FavoriteEventRoom
import com.fwahyudianto.militant.data.repository.FavoriteEventRepository

object Injection {
    fun provideRepository(context: Context): FavoriteEventRepository {
        val database = FavoriteEventRoom.getDatabase(context)
        val iFavorite = database.iFavoriteEvent()

        return FavoriteEventRepository.getInstance(iFavorite)
    }
}