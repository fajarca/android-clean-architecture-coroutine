package io.fajarca.home.domain.repository

import androidx.paging.DataSource
import io.fajarca.core.database.NewsEntity
import io.fajarca.home.domain.entities.News

interface NewsRepository {
    suspend fun getNewsFromApi(country : String, page : Int, pageSize : Int, onSuccess: () -> Unit) : List<NewsEntity>
    suspend fun insertNews(news : List<NewsEntity>)
    suspend fun findAllNews(country: String, page: Int, pageSize: Int, onSuccessAction : () -> Unit)
    fun getNewsFactory() : DataSource.Factory<Int, News>
}


