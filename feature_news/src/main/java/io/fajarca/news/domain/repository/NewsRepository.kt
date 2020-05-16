package io.fajarca.news.domain.repository

import androidx.paging.DataSource
import io.fajarca.core.network.HttpResult
import io.fajarca.core.vo.Result
import io.fajarca.news.domain.entities.News

interface NewsRepository {
    suspend fun refreshNews(country: String?, category : String?, page: Int, pageSize: Int) : Result<List<News>>
    suspend fun findAllNews(country: String?, category : String?, page: Int, pageSize: Int, onSuccessAction : () -> Unit, onErrorAction: (cause : HttpResult, code : Int, errorMessage : String) -> Unit)
    fun findByCountry(country: String?) : DataSource.Factory<Int, News>
    fun findByCategory(category: String?) : DataSource.Factory<Int, News>
    fun findAll() : DataSource.Factory<Int, News>
}


