package io.fajarca.home.domain.usecase

import androidx.paging.DataSource
import io.fajarca.core.database.NewsEntity
import io.fajarca.core.vo.Result
import io.fajarca.home.data.response.NewsDto
import io.fajarca.home.domain.entities.News
import io.fajarca.home.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(page : Int, pageSize : Int, onSuccess : () -> Unit, onError : () -> Unit): List<News> {
        return repository.getNews(page, pageSize, onSuccess, onError)
    }

    suspend fun fetchFromApi(page : Int, pageSize : Int): Result<NewsDto> {
        return repository.getNewsFromApi(country = "id", page = page, pageSize = pageSize)
    }

    fun fetchFromDb(): DataSource.Factory<Int, NewsEntity> {
        return repository.findAllNews()
    }

    suspend fun insertAll(news : List<NewsEntity>) {
        repository.insertNews(news)
    }


}