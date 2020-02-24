package io.fajarca.news_channel.presentation

import androidx.lifecycle.*
import io.fajarca.core.vo.Result
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.usecase.GetNewsChannelUseCase
import io.fajarca.news_channel.presentation.mapper.NewsChannelPresentationMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsChannelViewModel (private val getNewsChannelUseCase: GetNewsChannelUseCase, private val mapper : NewsChannelPresentationMapper) : ViewModel() {

    class Factory @Inject constructor(
        private val getNewsChannelUseCase: GetNewsChannelUseCase,
        private val mapper : NewsChannelPresentationMapper
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsChannelViewModel::class.java)) {
                return NewsChannelViewModel(getNewsChannelUseCase, mapper) as T
            }
            throw IllegalArgumentException("ViewModel not found")
        }
    }

    private val _newsChannel = MutableLiveData<Result<List<NewsChannel>>>()
    val newsChannel : LiveData<Result<List<NewsChannel>>> = _newsChannel

    fun getNewsChannel() {
        setResult(Result.Loading)
        viewModelScope.launch {
            val result = getNewsChannelUseCase.execute()
            if (result.isEmpty()) {
                setResult(Result.Empty)
            } else {
                _newsChannel.value = Result.Success(mapper.map(result))
            }
        }
    }

    private fun setResult(result : Result<List<NewsChannel>>) {
        _newsChannel.value = result
    }


}