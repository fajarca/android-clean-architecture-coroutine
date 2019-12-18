package io.fajarca.todo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.todo.data.local.entity.Todo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TodoDao {
/*

    @Query("SELECT * FROM todos")
    abstract fun findAll(): LiveData<List<Todo>>
*/


    @Query("SELECT * FROM todos")
    abstract fun findAll(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(todo: Todo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(todos: List<Todo>)

    @Query("DELETE FROM todos WHERE id =:id")
    abstract fun deleteById(id: Long)
/*
    *//**
     * Execute multiple queries in single transaction
     *//*
    @Transaction
    open suspend fun deleteAndInsertInTransaction(movieId: Long, casts: List<Todo>) {
        deleteById(movieId)
        insertAll(casts)
    }*/
}