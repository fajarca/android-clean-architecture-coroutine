package io.fajarca.news_channel.domain.usecase

import io.fajarca.news_channel.domain.entities.ChannelContent
import io.fajarca.news_channel.domain.entities.ChannelHeader
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.entities.NewsChannelItem
import io.fajarca.news_channel.domain.repository.NewsChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetNewsChannelUseCase @Inject constructor(private val repository: NewsChannelRepository) {

    suspend operator fun invoke(): Flow<List<NewsChannelItem>> {
        return repository.findAllNewsChannel()
            .map { newsChannel ->
                val uniqueCountryNames = getUniqueCountryNames(newsChannel)
                groupData(uniqueCountryNames, newsChannel)
            }
    }

    private fun getUniqueCountryNames(newsChannel: List<NewsChannel>): List<String> {
        return newsChannel
            .distinctBy { it.country }
            .sortedBy { it.country }
            .map { it.country }
    }

    private fun groupData(countryNames : List<String>, newsChannel: List<NewsChannel>): MutableList<NewsChannelItem> {
        val treeMap = TreeMap<String, List<NewsChannel>>()

        val recyclerViewItem = mutableListOf<NewsChannelItem>()

        countryNames.forEach { countryName  ->
            treeMap[countryName] = emptyList()
        }

        for (key in treeMap.keys) {
            //Add header
            recyclerViewItem.add(ChannelHeader(key))

            for (i in newsChannel) {
                //Add content
                if (i.country == key) {
                    recyclerViewItem.add(ChannelContent(i))
                }
            }

        }
        return recyclerViewItem
    }

}