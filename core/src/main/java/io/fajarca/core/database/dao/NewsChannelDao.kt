package io.fajarca.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.core.database.entity.NewsChannelEntity

@Dao
abstract class NewsChannelDao {

    @Query("SELECT * FROM news_channels")
    abstract fun findAll(): List<NewsChannelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(newsChannels: List<NewsChannelEntity>)
}