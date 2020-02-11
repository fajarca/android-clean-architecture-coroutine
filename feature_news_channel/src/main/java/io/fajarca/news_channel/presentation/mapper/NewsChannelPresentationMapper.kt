package io.fajarca.news_channel.presentation.mapper

import io.fajarca.core.mapper.Mapper
import io.fajarca.news_channel.domain.entities.NewsChannel
import java.util.*
import javax.inject.Inject

class NewsChannelPresentationMapper @Inject constructor(): Mapper<List<NewsChannel>, List<NewsChannel>>(){

    override fun map(input: List<NewsChannel>): List<NewsChannel> {
        val newsChannel = mutableListOf<NewsChannel>()
        input.map {
            val locale = Locale("en", it.country)
            val country = locale.displayCountry
            newsChannel.add(NewsChannel(it.id, country, it.name, it.url))
        }
        return newsChannel
    }

}