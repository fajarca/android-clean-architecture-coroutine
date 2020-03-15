package io.fajarca.news_channel.presentation.mapper

import io.fajarca.core.mapper.AsyncMapper
import io.fajarca.news_channel.domain.entities.NewsChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class NewsChannelPresentationMapper @Inject constructor() : AsyncMapper<List<NewsChannel>, List<NewsChannel>>() {

    override suspend fun map(dispatcher: CoroutineDispatcher, input: List<NewsChannel>): List<NewsChannel> {
        return withContext(dispatcher) {
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