package io.fajarca.home.data.source


import io.fajarca.home.data.NewsService
import io.fajarca.core.network.RemoteDataSource
import io.fajarca.core.vo.Result
import io.fajarca.home.data.response.NewsDto
import kotlinx.coroutines.CoroutineDispatcher

class NewsRemoteDataSource (private val newsService: NewsService) : RemoteDataSource() {

    suspend fun getTopHeadlines(dispatcher: CoroutineDispatcher, country : String = "id", page : Int = 1, pageSize : Int = 25): Result<NewsDto> {
        return safeApiCall(dispatcher) { newsService.getTopHeadlines(country, page, pageSize) }
    }

}