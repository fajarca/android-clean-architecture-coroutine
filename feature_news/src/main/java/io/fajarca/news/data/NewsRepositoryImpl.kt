package io.fajarca.news.data

import androidx.paging.DataSource
import io.fajarca.core.database.dao.NewsDao
import io.fajarca.core.database.entity.NewsEntity
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.dispatcher.DispatcherProvider
import io.fajarca.core.network.HttpResult
import io.fajarca.core.vo.Result
import io.fajarca.news.data.mapper.NewsMapper
import io.fajarca.news.data.response.NewsDto
import io.fajarca.news.data.source.NewsRemoteDataSource
import io.fajarca.news.domain.entities.News
import io.fajarca.news.domain.repository.NewsRepository
import okhttp3.Dispatcher
import timber.log.Timber
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val mapper: NewsMapper,
    private val dao: NewsDao,
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNewsFromApi(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction: () -> Unit): Result<NewsDto> {
        return remoteDataSource.getNews(dispatcher.io, country, category, page, pageSize)
    }


    override suspend fun insertNews(news: List<NewsEntity>) {
        dao.insertAll(news)
    }

    override suspend fun findAllNews(country: String?, category: String?, page: Int, pageSize: Int, onSuccessAction: () -> Unit, onErrorAction: (cause: HttpResult, code : Int, errorMessage : String) -> Unit) {
        val apiResult = getNewsFromApi(country, category, page, pageSize, onSuccessAction)
        when(apiResult) {
            is Result.Success -> {
                onSuccessAction()
                val news =  mapper.map(country, category, apiResult.data)
                insertNews(news)
            }
            is Result.Error -> {
                onErrorAction(apiResult.cause, apiResult.code ?: 0, apiResult.errorMessage ?: "")
            }
        }
    }


    override fun findByCountry(country: String?): DataSource.Factory<Int, News> {
        return dao.findByCountry(country ?: "").map { mapper.mapToDomain(it) }
    }

    override fun findByCategory(category: String?): DataSource.Factory<Int, News> {
        return dao.findByCategory(category ?: "").map { mapper.mapToDomain(it) }
    }

    override fun findAll(): DataSource.Factory<Int, News> {
        return dao.findAll().map { mapper.mapToDomain(it) }
    }
}