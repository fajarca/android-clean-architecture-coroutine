package io.fajarca.news_channel.domain.repository

import io.fajarca.core.database.entity.NewsChannelEntity
import io.fajarca.news_channel.domain.entities.NewsChannel

interface NewsChannelRepository {
    suspend fun getNewsChannelFromApi() : List<NewsChannel>
    suspend fun insertNewsChannel(newsChannel : List<NewsChannelEntity>)
    suspend fun findAllNewsChannel() : List<NewsChannel>
}


