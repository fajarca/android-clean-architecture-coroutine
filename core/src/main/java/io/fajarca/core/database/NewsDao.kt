package io.fajarca.core.database

import androidx.paging.DataSource
import androidx.room.*

@Dao
abstract class NewsDao {

    @Query("SELECT * FROM news ORDER BY publishedAt DESC LIMIT :limit")
    abstract fun findTopHeadlines(limit : Int): List<NewsEntity>

    @Query("SELECT title FROM news ORDER BY publishedAt DESC LIMIT :limit")
    abstract fun findTopHeadlinesTitle(limit : Int): List<String>

    @Query("SELECT * FROM news WHERE title NOT IN (:topHeadlinesTitle) ORDER BY publishedAt DESC")
    abstract fun findAllNews(topHeadlinesTitle: List<String>): List<NewsEntity>


    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    abstract fun findAllNews(): DataSource.Factory<Int, NewsEntity>

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