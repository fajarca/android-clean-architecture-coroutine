package io.fajarca.news.data

import androidx.paging.DataSource
import io.fajarca.core.database.dao.NewsDao
import io.fajarca.core.database.entity.NewsEntity
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.vo.Result
import io.fajarca.news.data.mapper.NewsMapper
import io.fajarca.news.data.source.NewsRemoteDataSource
import io.fajarca.news.domain.entities.News
import io.fajarca.news.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper: NewsMapper,
    private val dao: NewsDao,
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNewsFromApi(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction: () -> Unit): List<NewsEntity> {
        val apiResult = remoteDataSource.getNews(dispatcher.io, country, category, page, pageSize)
        if (apiResult is Result.Success) {
            onSuccessAction()
            return mapper.map(country, category, apiResult.data)
        }

        return emptyList()
    }


    override suspend fun insertNews(news: List<NewsEntity>) {
        dao.insertAll(news)
    }


    override suspend fun findAllNews(country: String?, category : String?, page: Int, pageSize: Int, onSuccessAction: () -> Unit) {
        val result = getNewsFromApi(country, category, page, pageSize, onSuccessAction)
        insertNews(result)
    }

    override fun getNewsFactory(country: String?, category: String?): DataSource.Factory<Int, News> {
        if (category.isNullOrEmpty()) {
            return dao.findByCountry(country ?: "").map { mapper.mapToDomain(it) }
        }

        return dao.findByCategory(category).map { mapper.mapToDomain(it) }
    }
}