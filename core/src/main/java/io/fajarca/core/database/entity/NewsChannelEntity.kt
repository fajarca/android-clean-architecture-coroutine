package io.fajarca.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_channels")
data class NewsChannelEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "country")
    var country: String = "",
    @ColumnInfo(name = "url")
    var url: String = ""
)