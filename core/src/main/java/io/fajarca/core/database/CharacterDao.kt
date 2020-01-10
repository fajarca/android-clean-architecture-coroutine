package io.fajarca.core.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CharacterDao {

    @Query("SELECT * FROM marvel_characters")
    abstract fun findAll(): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("DELETE FROM marvel_characters WHERE id =:id")
    abstract fun deleteById(id: Long)


  /*  *//**
     * Execute multiple queries in single transaction
     *//*
    @Transaction
    open suspend fun deleteAndInsertInTransaction(movieId: Long, casts: List<CharacterEntity>) {
        deleteById(movieId)
        insertAll(casts)
    }*/
}