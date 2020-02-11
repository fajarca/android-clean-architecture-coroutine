package io.fajarca.news_channel.data.mapper

import io.fajarca.core.database.entity.NewsChannelEntity
import io.fajarca.core.mapper.Mapper
import io.fajarca.news_channel.data.response.SourcesDto
import io.fajarca.news_channel.domain.entities.NewsChannel

class NewsChannelMapper : Mapper<SourcesDto, List<NewsChannelEntity>>(){

    override fun map(input: SourcesDto): List<NewsChannelEntity> {
        val newsChannel = mutableListOf<NewsChannelEntity>()
        input.sources?.map {
            newsChannel.add(NewsChannelEntity(it?.id ?: "", it?.name ?: "", it?.country ?: "", it?.url ?: ""))
        }
        return newsChannel
    }

    fun mapToDomain(input : List<NewsChannelEntity>): List<NewsChannel> {
        val newsChannel = mutableListOf<NewsChannel>()
        input.map {
            newsChannel.add(NewsChannel(it.id, it.country, it.name, it.url))
        }
        return newsChannel
    }

}