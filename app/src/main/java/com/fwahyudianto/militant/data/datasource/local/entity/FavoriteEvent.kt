package com.fwahyudianto.militant.data.datasource.local.entity

//  Import Library
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "event_favorite")
@Parcelize
data class FavoriteEvent(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "image")
    var image: String? = null,

    @ColumnInfo(name = "category")
    var category: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "summary")
    var summary: String? = null,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
) : Parcelable