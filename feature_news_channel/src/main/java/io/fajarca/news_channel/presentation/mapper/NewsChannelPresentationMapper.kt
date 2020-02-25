package io.fajarca.news_channel.presentation.mapper

import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.mapper.Mapper
import io.fajarca.news_channel.domain.entities.NewsChannel
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class NewsChannelPresentationMapper @Inject constructor(private val dispatcherProvider: CoroutineDispatcherProvider): Mapper<List<NewsChannel>, List<NewsChannel>>(){

    override suspend fun map(input: List<NewsChannel>): List<NewsChannel> {
        return withContext(dispatcherProvider.default) {
            val newsChannel = mutableListOf<NewsChannel>()
            input.map {
                val locale = Locale("en", it.country)
                val country = locale.displayCountry
                newsChannel.add(NewsChannel(it.id, country, it.name, it.url, it.newsIntiial))
            }
            newsChannel
        }

    }

}