package io.fajarca.home.data

import androidx.paging.DataSource
import io.fajarca.core.database.NewsDao
import io.fajarca.core.database.NewsEntity
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.vo.Result
import io.fajarca.home.data.mapper.NewsMapper
import io.fajarca.home.data.source.NewsRemoteDataSource
import io.fajarca.home.domain.entities.News
import io.fajarca.home.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper: NewsMapper,
    private val dao: NewsDao,
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNewsFromApi(country : String, page : Int, pageSize : Int, onSuccess: () -> Unit): List<NewsEntity> {
        val apiResult = remoteDataSource.getTopHeadlines(dispatcher.io, country, page, pageSize)
        if (apiResult is Result.Success) {
            onSuccess()
            return mapper.map(apiResult.data)
        }

        return emptyList()
    }


    override suspend fun insertNews(news: List<NewsEntity>) {
        dao.insertAll(news)
    }


    override suspend fun findAllNews(country: String, page: Int, pageSize: Int, onSuccess: () -> Unit) {
        val result = getNewsFromApi(country, page, pageSize, onSuccess)
        insertNews(result)
    }

    override fun getNewsFactory(): DataSource.Factory<Int, News> {
        return dao.findAllNews().map { mapper.mapToDomain(it) }
    }

}