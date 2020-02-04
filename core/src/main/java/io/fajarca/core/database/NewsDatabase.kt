package io.fajarca.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.core.database.dao.NewsChannelDao
import io.fajarca.core.database.dao.NewsDao
import io.fajarca.core.database.entity.NewsChannelEntity
import io.fajarca.core.database.entity.NewsEntity


@Database(entities = [NewsEntity::class, NewsChannelEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun newsChannelDao() : NewsChannelDao
}