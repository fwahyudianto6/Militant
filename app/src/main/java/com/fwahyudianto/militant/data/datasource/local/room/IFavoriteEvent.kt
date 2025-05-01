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
    @Query("SELECT * from event_favorite ORDER BY id DESC")
    fun getEvents(): LiveData<List<FavoriteEvent>>

    @Query("SELECT * FROM event_favorite WHERE id = :eventId")
    fun getFavoriteById(eventId: Int): LiveData<List<FavoriteEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(addEvent: FavoriteEvent)

    @Update
    suspend fun updateFavorite(editEvent: FavoriteEvent)

    @Delete
    suspend fun deleteFavorite(deleteEvent: FavoriteEvent)

    @Query("DELETE FROM event_favorite WHERE id = :eventId")
    suspend fun deleteFavoriteById(eventId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM event_favorite WHERE id = :id)")
    fun isFavorite(id: String): Boolean
}