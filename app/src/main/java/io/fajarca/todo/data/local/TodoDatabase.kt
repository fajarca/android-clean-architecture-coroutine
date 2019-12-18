package io.fajarca.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.todo.data.local.dao.TodoDao
import io.fajarca.todo.data.local.entity.Todo
import io.fajarca.todo.util.Converters

@Database(entities = [Todo::class], version = 1)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}