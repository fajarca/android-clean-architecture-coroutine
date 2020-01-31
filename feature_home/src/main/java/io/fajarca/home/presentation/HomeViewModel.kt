package io.fajarca.home.presentation

import androidx.lifecycle.*
import io.fajarca.home.presentation.mapper.TopHeadlinePresentationMapper
import io.fajarca.home.domain.entities.TopHeadline
import io.fajarca.home.domain.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel (private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase, private val mapper: TopHeadlinePresentationMapper) : ViewModel() {

    class Factory @Inject constructor(
        private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
        private val mapper: TopHeadlinePresentationMapper
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(
                    getTopHeadlinesUseCase,
                    mapper
                ) as T
            }
            throw IllegalArgumentException("ViewModel not found")
        }
    }

    private val _topHeadlines = MutableLiveData<TopHeadlinesState<List<TopHeadline>>>()
    val topHeadlines: LiveData<TopHeadlinesState<List<TopHeadline>>>
        get() = _topHeadlines

    sealed class TopHeadlinesState<out T> {
        object Empty : TopHeadlinesState<Nothing>()
        object Loading : TopHeadlinesState<Nothing>()
        data class Success<out T>(val data: T) : TopHeadlinesState<T>()
    }

    fun getTopHeadlines() {
        _topHeadlines.value =
            TopHeadlinesState.Loading
        viewModelScope.launch {
            val headlines = getTopHeadlinesUseCase.execute()
            if (headlines.isEmpty()) _topHeadlines.value =
                TopHeadlinesState.Empty else _topHeadlines.value =
                TopHeadlinesState.Success(
                    mapper.map(headlines)
                )
        }
    }



}