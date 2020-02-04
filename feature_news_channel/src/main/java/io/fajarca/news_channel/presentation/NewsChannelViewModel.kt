package io.fajarca.news_channel.presentation

import androidx.lifecycle.*
import io.fajarca.core.vo.Result
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.usecase.GetNewsChannelUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsChannelViewModel (private val getNewsChannelUseCase: GetNewsChannelUseCase) : ViewModel() {

    class Factory @Inject constructor(
        private val getNewsChannelUseCase: GetNewsChannelUseCase
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsChannelViewModel::class.java)) {
                return NewsChannelViewModel(getNewsChannelUseCase) as T
            }
            throw IllegalArgumentException("ViewModel not found")
        }
    }

    private val _newsChannel = MutableLiveData<Result<List<NewsChannel>>>()
    val newsChannel : LiveData<Result<List<NewsChannel>>> = _newsChannel

    fun getNewsChannel() {
        _newsChannel.value = Result.Loading
        viewModelScope.launch {
            val result = getNewsChannelUseCase.execute()
            _newsChannel.value = Result.Success(result)
        }
    }


}