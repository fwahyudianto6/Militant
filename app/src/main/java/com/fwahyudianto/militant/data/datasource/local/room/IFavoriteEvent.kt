package com.fwahyudianto.militant.data.datasource.local.room

//  Import Library
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fwahyudianto.militant.data.datasource.local.entity.FavoriteEvent

@Dao
interface IFavoriteEvent {
    @Query("SELECT * from event_favorite ORDER BY id ASC")
    fun getEvents(): LiveData<List<FavoriteEvent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(addEvent: FavoriteEvent)

    @Update
    fun updateFavorite(editEvent: FavoriteEvent)

    @Delete
    fun deleteFavorite(deleteEvent: FavoriteEvent)
}