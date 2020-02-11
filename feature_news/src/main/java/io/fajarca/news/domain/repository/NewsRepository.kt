package io.fajarca.news.domain.repository

import androidx.paging.DataSource
import io.fajarca.core.database.entity.NewsEntity
import io.fajarca.news.domain.entities.News

interface NewsRepository {
    suspend fun getNewsFromApi(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction: () -> Unit) : List<NewsEntity>
    suspend fun insertNews(news : List<NewsEntity>)
    suspend fun findAllNews(country: String?, category : String?, page: Int, pageSize: Int, onSuccessAction : () -> Unit)
    fun getNewsFactory(country: String?, category : String?) : DataSource.Factory<Int, News>
}


