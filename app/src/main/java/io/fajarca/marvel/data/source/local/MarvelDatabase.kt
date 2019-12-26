package io.fajarca.marvel.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.marvel.core.Converters
import io.fajarca.marvel.domain.model.local.Character

@Database(entities = [Character::class], version = 1)
@TypeConverters(Converters::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}