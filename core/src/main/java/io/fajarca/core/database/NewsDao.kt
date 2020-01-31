package io.fajarca.core.database

import androidx.room.*

@Dao
abstract class NewsDao {
    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    abstract fun findAll(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(characters: List<NewsEntity>)

    @Query("DELETE FROM news")
    abstract suspend fun deleteAll()
    /**
     * Execute multiple queries in single transaction
     */
    @Transaction
    open suspend fun deleteAndInsertInTransaction(topHeadlines : List<NewsEntity>) {
        deleteAll()
        insertAll(topHeadlines)
    }
}