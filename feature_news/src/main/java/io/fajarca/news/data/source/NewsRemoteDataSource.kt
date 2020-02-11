package io.fajarca.news.data.source


import io.fajarca.core.network.RemoteDataSource
import io.fajarca.core.vo.Result
import io.fajarca.news.data.NewsService
import io.fajarca.news.data.response.NewsDto
import kotlinx.coroutines.CoroutineDispatcher

class NewsRemoteDataSource (private val newsService: NewsService) : RemoteDataSource() {

    suspend fun getNews(dispatcher: CoroutineDispatcher, country : String?, category : String?, page : Int, pageSize : Int): Result<NewsDto> {
        return safeApiCall(dispatcher) { newsService.getNews(country, category, page, pageSize) }
    }

}