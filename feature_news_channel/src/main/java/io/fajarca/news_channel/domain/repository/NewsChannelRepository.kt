package io.fajarca.news_channel.domain.repository

import io.fajarca.core.database.entity.NewsChannelEntity
import io.fajarca.core.vo.Result
import io.fajarca.news_channel.data.response.SourcesDto
import io.fajarca.news_channel.domain.entities.NewsChannel
import kotlinx.coroutines.flow.Flow

interface NewsChannelRepository {
    suspend fun getNewsChannelFromApi() : Result<SourcesDto>
    suspend fun getNewsChannelFromDb() : List<NewsChannel>
    suspend fun insertNewsChannel(newsChannel : List<NewsChannelEntity>)
    suspend fun findAllNewsChannel() : Flow<List<NewsChannel>>
}


