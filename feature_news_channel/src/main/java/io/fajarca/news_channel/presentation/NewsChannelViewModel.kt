package io.fajarca.news_channel.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.news_channel.domain.entities.ChannelContent
import io.fajarca.news_channel.domain.entities.ChannelHeader
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.entities.NewsChannelItem
import io.fajarca.news_channel.domain.usecase.GetNewsChannelUseCase
import io.fajarca.news_channel.presentation.mapper.NewsChannelPresentationMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class NewsChannelViewModel @Inject constructor(private val getNewsChannelUseCase: GetNewsChannelUseCase,
                                               private val mapper : NewsChannelPresentationMapper,
                                               private val dispatcherProvider: CoroutineDispatcherProvider) : ViewModel() {

    private val _newsChannel = MutableLiveData<NewsChannelState>()
    val newsChannel : LiveData<NewsChannelState> = _newsChannel

    sealed class NewsChannelState {
        object Loading: NewsChannelState()
        data class Success(val channels : List<NewsChannelItem>) : NewsChannelState()
        object Empty : NewsChannelState()
    }

    fun getNewsChannel() {
        viewModelScope.launch {
            getNewsChannelUseCase()
                .onStart { setResult(NewsChannelState.Loading) }
                .map {
                   mapper.map(dispatcherProvider.default, it)
                }
                .collect {
                    val uniqueCountryNames = getUniqueCountryNames(it)
                    val output = groupData(uniqueCountryNames, it)
                    if (it.isEmpty()) {
                        setResult(NewsChannelState.Empty)
                    } else {
                        setResult(NewsChannelState.Success(output))
                    }
                }

        }
    }

    private fun setResult(result : NewsChannelState) {
        _newsChannel.value = result
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
            val header = countryName
            treeMap[header] = emptyList()
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