package io.fajarca.home.data

import io.fajarca.core.database.NewsDao
import io.fajarca.core.database.NewsEntity
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.vo.Result
import io.fajarca.home.data.mapper.NewsMapper
import io.fajarca.home.data.response.NewsDto
import io.fajarca.home.data.source.NewsRemoteDataSource
import io.fajarca.home.domain.entities.News
import io.fajarca.home.domain.repository.NewsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper: NewsMapper,
    private val dao: NewsDao,
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNewsFromApi(country : String, page : Int, pageSize : Int): Result<NewsDto> {
        return remoteDataSource.getTopHeadlines(dispatcher.io, country, page, pageSize)
    }

    override suspend fun getNewsFromDb(): List<NewsEntity> {
        return withContext(dispatcher.io) {
            dao.findAllNews()
        }
    }

    override suspend fun getNews(): List<News> {
        val apiResult = getNewsFromApi()

        if (apiResult is Result.Success) {
            insertNews(mapper.map(apiResult.data))
        }

        return mapper.mapToDomain(getNewsFromDb())
    }

    override suspend fun insertNews(topHeadlines: List<NewsEntity>) {
        dao.deleteAndInsertInTransaction(topHeadlines)
    }

    override suspend fun getTopHeadlines(limit: Int): List<News> {
        val headlines  = withContext(dispatcher.io) {
            dao.findTopHeadlines(limit)
        }

        return mapper.mapToDomain(headlines)
    }


}