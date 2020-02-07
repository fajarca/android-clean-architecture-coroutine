package io.fajarca.home.domain.repository

import androidx.paging.DataSource
import io.fajarca.core.database.entity.NewsEntity
import io.fajarca.home.domain.entities.News

interface NewsRepository {
    suspend fun getNewsFromApi(country : String, category : String?, page : Int, pageSize : Int, onSuccessAction: () -> Unit) : List<NewsEntity>
    suspend fun insertNews(news : List<NewsEntity>)
    suspend fun findAllNews(country: String, category : String?, page: Int, pageSize: Int, onSuccessAction : () -> Unit)
    fun getNewsFactory() : DataSource.Factory<Int, News>
}


