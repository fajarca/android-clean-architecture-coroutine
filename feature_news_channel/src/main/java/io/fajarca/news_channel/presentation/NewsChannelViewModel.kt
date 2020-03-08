package io.fajarca.news_channel.presentation

import androidx.lifecycle.*
import io.fajarca.core.vo.Result
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.usecase.GetNewsChannelUseCase
import io.fajarca.news_channel.presentation.mapper.NewsChannelPresentationMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsChannelViewModel @Inject constructor(private val getNewsChannelUseCase: GetNewsChannelUseCase, private val mapper : NewsChannelPresentationMapper) : ViewModel() {

    private val _newsChannel = MutableLiveData<NewsChannelState>()
    val newsChannel : LiveData<NewsChannelState> = _newsChannel

    sealed class NewsChannelState {
        object Loading: NewsChannelState()
        data class Success(val channels : List<NewsChannel>) : NewsChannelState()
        object Empty : NewsChannelState()
    }

    fun getNewsChannel() {
        setResult(NewsChannelState.Loading)
        viewModelScope.launch {
            val result = getNewsChannelUseCase()
            if (result.isEmpty()) {
                setResult(NewsChannelState.Empty)
            } else {
                setResult(NewsChannelState.Success(mapper.map(result)))
            }
        }
    }

    private fun setResult(result : NewsChannelState) {
        _newsChannel.value = result
    }


}