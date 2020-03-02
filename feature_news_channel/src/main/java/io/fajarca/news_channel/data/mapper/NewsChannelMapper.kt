package io.fajarca.news_channel.data.mapper

import io.fajarca.core.database.entity.NewsChannelEntity
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.mapper.AsyncMapper
import io.fajarca.core.mapper.Mapper
import io.fajarca.news_channel.data.response.SourcesDto
import io.fajarca.news_channel.domain.entities.NewsChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsChannelMapper @Inject constructor() : AsyncMapper<SourcesDto, List<NewsChannelEntity>>(){

    override suspend fun map(dispatcher : CoroutineDispatcher, input: SourcesDto): List<NewsChannelEntity> {
        return withContext(dispatcher) {
            val newsChannel = mutableListOf<NewsChannelEntity>()
            input.sources?.map {
                newsChannel.add(NewsChannelEntity(it?.id ?: "", it?.name ?: "", it?.country ?: "", it?.url ?: ""))
            }
            newsChannel
        }
    }

    suspend fun mapToDomain(dispatcher: CoroutineDispatcher, input : List<NewsChannelEntity>): List<NewsChannel> {
        return withContext(dispatcher) {
            val newsChannel = mutableListOf<NewsChannel>()
            input.map {
                newsChannel.add(NewsChannel(it.id, it.country, it.name, it.url, getChannelInitial(it.name)))
            }
            newsChannel
        }
    }

    private fun getChannelInitial(channelName : String) : String {
        val splittedChannelName = channelName.split(" ")
        val size = splittedChannelName.size

        if (splittedChannelName.isNotEmpty()) {
            return when(size) {
                1 -> getFirstCharacter(splittedChannelName[0])
                2 -> getFirstCharacter(splittedChannelName[0]) + getFirstCharacter(splittedChannelName[1])
                3 -> getFirstCharacter(splittedChannelName[0]) + getFirstCharacter(splittedChannelName[2])
                4 -> getFirstCharacter(splittedChannelName[0]) + getFirstCharacter(splittedChannelName[3])
                else -> ""
            }
        }

        return ""
    }

    private fun getFirstCharacter(input : String) : String {
        if (input.length > 1) {
            return input.substring(0, 1)
        }
        return ""
    }
}