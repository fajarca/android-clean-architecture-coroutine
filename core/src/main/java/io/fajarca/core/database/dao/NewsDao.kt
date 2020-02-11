package io.fajarca.core.database.dao

import androidx.paging.DataSource
import androidx.room.*
import io.fajarca.core.database.entity.NewsEntity

@Dao
abstract class NewsDao {

    @Query("SELECT * FROM news WHERE country = :country ORDER BY publishedAt DESC")
    abstract fun findByCountry(country : String): DataSource.Factory<Int, NewsEntity>

    @Query("SELECT * FROM news WHERE category = :category ORDER BY publishedAt DESC")
    abstract fun findByCategory(category : String): DataSource.Factory<Int, NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(news: List<NewsEntity>)

    @Query("DELETE FROM news")
    abstract suspend fun deleteAll()
    /**
     * Execute multiple queries in single transaction
     */
    @Transaction
    open suspend fun deleteAndInsertInTransaction(news : List<NewsEntity>) {
        deleteAll()
        insertAll(news)
    }
}