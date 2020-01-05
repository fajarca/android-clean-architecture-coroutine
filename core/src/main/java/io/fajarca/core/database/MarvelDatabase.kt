package io.fajarca.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Character::class], version = 1)
@TypeConverters(Converters::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}