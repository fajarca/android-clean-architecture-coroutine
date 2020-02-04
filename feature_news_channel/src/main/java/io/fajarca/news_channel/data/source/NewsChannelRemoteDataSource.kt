package io.fajarca.news_channel.data.source


import io.fajarca.core.network.RemoteDataSource
import io.fajarca.core.vo.Result
import io.fajarca.news_channel.data.ChannelService
import io.fajarca.news_channel.data.response.SourcesDto
import kotlinx.coroutines.CoroutineDispatcher

class NewsChannelRemoteDataSource (private val channelService: ChannelService) : RemoteDataSource() {

    suspend fun getNewsChannel(dispatcher: CoroutineDispatcher): Result<SourcesDto> {
        return safeApiCall(dispatcher) { channelService.getNewsChannels() }
    }

}