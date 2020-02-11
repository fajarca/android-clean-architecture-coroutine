package io.fajarca.news_channel.data

import io.fajarca.core.database.dao.NewsChannelDao
import io.fajarca.core.database.entity.NewsChannelEntity
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.vo.Result
import io.fajarca.news_channel.data.mapper.NewsChannelMapper
import io.fajarca.news_channel.data.response.SourcesDto
import io.fajarca.news_channel.data.source.NewsChannelRemoteDataSource
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.repository.NewsChannelRepository
import javax.inject.Inject

class NewsChannelRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper: NewsChannelMapper,
    private val dao: NewsChannelDao,
    private val remoteDataSource: NewsChannelRemoteDataSource
) : NewsChannelRepository {


    override suspend fun getNewsChannelFromApi(): Result<SourcesDto> {
        return remoteDataSource.getNewsChannel(dispatcher.io)
    }

    override suspend fun getNewsChannelFromDb(): List<NewsChannel> {
        return mapper.mapToDomain(dao.findAll())
    }

    override suspend fun insertNewsChannel(newsChannel: List<NewsChannelEntity>) {
        dao.insertAll(newsChannel)
    }

    override suspend fun findAllNewsChannel(): List<NewsChannel> {
        val apiResult = getNewsChannelFromApi()
        if (apiResult is Result.Success) {
            val newsChannel = mapper.map(apiResult.data)
            insertNewsChannel(newsChannel)
        }
        return getNewsChannelFromDb()
    }

}