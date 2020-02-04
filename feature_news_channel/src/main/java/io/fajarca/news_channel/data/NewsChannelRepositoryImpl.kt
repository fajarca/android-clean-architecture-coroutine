package io.fajarca.news_channel.data

import io.fajarca.core.database.dao.NewsChannelDao
import io.fajarca.core.database.entity.NewsChannelEntity
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.vo.Result
import io.fajarca.news_channel.data.mapper.NewsChannelMapper
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


    override suspend fun getNewsChannelFromApi(): List<NewsChannel> {
        val apiResult = remoteDataSource.getNewsChannel(dispatcher.io)
        if (apiResult is Result.Success) {
            val newsChannel  = mapper.map(apiResult.data)
            insertNewsChannel(newsChannel)
        }

        return emptyList()
    }

    override suspend fun insertNewsChannel(newsChannel: List<NewsChannelEntity>) {
        dao.insertAll(newsChannel)
    }

    override suspend fun findAllNewsChannel(): List<NewsChannel> {
        val newsChannel = dao.findAll()
        return mapper.mapToDomain(newsChannel)
    }

}