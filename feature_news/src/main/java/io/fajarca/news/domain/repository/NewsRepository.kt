package io.fajarca.news.domain.repository

import androidx.paging.DataSource
import io.fajarca.core.database.entity.NewsEntity
import io.fajarca.core.vo.Result
import io.fajarca.news.data.response.NewsDto
import io.fajarca.news.domain.entities.News

interface NewsRepository {
    suspend fun getNewsFromApi(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction: () -> Unit) : Result<NewsDto>
    suspend fun insertNews(news : List<NewsEntity>)
    suspend fun findAllNews(country: String?, category : String?, page: Int, pageSize: Int, onSuccessAction : () -> Unit)
    fun findByCountry(country: String?) : DataSource.Factory<Int, News>
    fun findByCategory(category: String?) : DataSource.Factory<Int, News>
}


