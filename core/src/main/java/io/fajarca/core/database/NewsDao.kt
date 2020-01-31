package io.fajarca.core.database

import androidx.room.*

@Dao
abstract class NewsDao {
    @Query("SELECT * FROM news ORDER BY publishedAt DESC LIMIT :limit")
    abstract fun findTopHeadlines(limit : Int): List<NewsEntity>

    @Query("SELECT id FROM news ORDER BY publishedAt DESC LIMIT :limit")
    abstract fun findTopHeadlinesIds(limit : Int): List<Int>

    @Query("SELECT * FROM news WHERE id NOT IN (:topHeadlinesIds) ORDER BY publishedAt DESC")
    abstract fun findAllNews(topHeadlinesIds: List<Int>): List<NewsEntity>

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