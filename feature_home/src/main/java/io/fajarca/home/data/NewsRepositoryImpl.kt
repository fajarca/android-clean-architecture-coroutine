package io.fajarca.home.data

import io.fajarca.core.database.TopHeadlineDao
import io.fajarca.core.database.TopHeadlineEntity
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.vo.Result
import io.fajarca.home.data.mapper.TopHeadlineMapper
import io.fajarca.home.data.response.TopHeadlinesDto
import io.fajarca.home.data.source.NewsRemoteDataSource
import io.fajarca.home.domain.entities.TopHeadline
import io.fajarca.home.domain.repository.NewsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper: TopHeadlineMapper,
    private val dao: TopHeadlineDao,
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getHeadlinesFromApi(): Result<TopHeadlinesDto> {
        return remoteDataSource.getTopHeadlines(dispatcher.io)
    }

    override suspend fun getHeadlinesFromDb(): List<TopHeadlineEntity> {
        return withContext(dispatcher.io) {
            dao.findAll()
        }
    }

    override suspend fun getHeadlines(): List<TopHeadline> {
        val apiResult = getHeadlinesFromApi()

        if (apiResult is Result.Success) {
            insertHeadlines(mapper.map(apiResult.data))
        }

        return mapper.mapToDomain(getHeadlinesFromDb())
    }

    override suspend fun insertHeadlines(topHeadlines: List<TopHeadlineEntity>) {
        dao.deleteAndInsertInTransaction(topHeadlines)
    }


}