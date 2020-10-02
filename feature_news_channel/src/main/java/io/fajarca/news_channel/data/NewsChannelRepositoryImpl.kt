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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        val savedChannel = dao.findAll()
        return mapper.mapToDomain(dispatcher.default, savedChannel)
    }

    override suspend fun insertNewsChannel(newsChannel: List<NewsChannelEntity>) {
        dao.insertAll(newsChannel)
    }

    override suspend fun findAllNewsChannel(): Flow<List<NewsChannel>> = flow {
        emit(getNewsChannelFromDb())
        val apiResult = getNewsChannelFromApi()
        if (apiResult is Result.Success) {
            val newsChannel = mapper.map(dispatcher.default, apiResult.data)
            insertNewsChannel(newsChannel)
        }
        emit(getNewsChannelFromDb())

    }

}