package com.fwahyudianto.militant.data.datasource.local.room

//  Import Library
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fwahyudianto.militant.data.datasource.local.entity.FavoriteEvent

@Database(entities = [FavoriteEvent::class], version = 1, exportSchema = false)
abstract class FavoriteEventRoom : RoomDatabase() {
    abstract fun iFavoriteEvent(): IFavoriteEvent

    companion object {
        @Volatile
        private var instance: FavoriteEventRoom? = null

        @JvmStatic
        fun getInstance(context: Context): FavoriteEventRoom =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteEventRoom::class.java, "DICODING.db"
                ).build()
            }
    }
}