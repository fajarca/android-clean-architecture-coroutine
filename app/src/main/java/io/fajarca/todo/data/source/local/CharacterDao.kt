package io.fajarca.todo.data.source.local

import androidx.room.*
import io.fajarca.todo.domain.model.local.Character
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CharacterDao {

    @Query("SELECT * FROM marvel_characters")
    abstract fun findAll(): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(characters: List<Character>)

    @Query("DELETE FROM marvel_characters WHERE id =:id")
    abstract fun deleteById(id: Long)


  /*  *//**
     * Execute multiple queries in single transaction
     *//*
    @Transaction
    open suspend fun deleteAndInsertInTransaction(movieId: Long, casts: List<Character>) {
        deleteById(movieId)
        insertAll(casts)
    }*/
}