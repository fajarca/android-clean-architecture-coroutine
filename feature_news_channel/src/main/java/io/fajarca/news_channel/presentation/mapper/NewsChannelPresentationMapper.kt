package io.fajarca.news_channel.presentation.mapper

import io.fajarca.core.mapper.Mapper
import io.fajarca.news_channel.domain.entities.ChannelContent
import io.fajarca.news_channel.domain.entities.ChannelHeader
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.entities.NewsChannelItem
import java.util.*
import javax.inject.Inject

class NewsChannelPresentationMapper @Inject constructor() : Mapper<List<NewsChannelItem>, List<NewsChannelItem>>() {

    override fun map(input: List<NewsChannelItem>): List<NewsChannelItem> {
        val newsChannel = mutableListOf<NewsChannelItem>()
        input.map {
            when (it) {
                is ChannelHeader -> {
                    val countryName = getReadableCountryName(it.name)
                    newsChannel.add(ChannelHeader(countryName))
                }
                is ChannelContent -> {
                    val channel = NewsChannel(it.newsChannel.id, getReadableCountryName(it.newsChannel.country), it.newsChannel.name, it.newsChannel.url, it.newsChannel.newsIntiial)
                    val content = ChannelContent(channel)
                    newsChannel.add(content)
                }
                else -> {}
            }
        }
        return newsChannel
    }

    private fun getReadableCountryName(countryName : String) : String {
        val locale = Locale("en", countryName)
        return locale.displayCountry
    }

}