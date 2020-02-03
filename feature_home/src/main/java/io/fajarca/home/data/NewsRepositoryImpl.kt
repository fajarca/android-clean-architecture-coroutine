package io.fajarca.home.data

import androidx.paging.DataSource
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

    companion object {
        const val TOP_HEADLINE_COUNT = 5
    }
    override suspend fun getNewsFromApi(country : String, page : Int, pageSize : Int): List<NewsEntity> {
        val apiResult = remoteDataSource.getTopHeadlines(dispatcher.io, country, page, pageSize)
        if (apiResult is Result.Success) {
            return mapper.map(apiResult.data)
        }

        return emptyList()
    }

    override suspend fun getNewsFromDb(): List<NewsEntity> {
        val topHeadlinesTitles = withContext(dispatcher.io) { dao.findTopHeadlinesTitle(TOP_HEADLINE_COUNT) }
        val news = withContext(dispatcher.io) { dao.findAllNews(topHeadlinesTitles) }
        return news
    }


    override suspend fun insertNews(topHeadlines: List<NewsEntity>) {
        dao.insertAll(topHeadlines)
    }

    override suspend fun getTopHeadlines(): List<News> {
        val headlines  = withContext(dispatcher.io) {
            dao.findTopHeadlines(TOP_HEADLINE_COUNT)
        }

        return mapper.mapToDomain(headlines)
    }

     override fun findAllNews(): DataSource.Factory<Int, NewsEntity> {
        return dao.findAllNews()
    }


}