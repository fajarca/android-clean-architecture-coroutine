package io.fajarca.news_channel.data

import io.fajarca.news_channel.data.response.SourcesDto
import retrofit2.http.GET


interface ChannelService {

    @GET("v2/sources")
    suspend fun getNewsChannels(): SourcesDto

}