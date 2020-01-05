package io.fajarca.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marvel_characters")
data class Character(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String = ""
)
