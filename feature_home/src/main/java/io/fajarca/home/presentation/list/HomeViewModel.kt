package io.fajarca.home.presentation.list

import androidx.lifecycle.*
import io.fajarca.home.domain.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.fajarca.home.domain.entities.TopHeadline

class HomeViewModel (private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase) :
    ViewModel() {

    class Factory @Inject constructor(
        private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(getTopHeadlinesUseCase) as T
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
        _topHeadlines.postValue(TopHeadlinesState.Loading)
        viewModelScope.launch {
            val characters = getTopHeadlinesUseCase.execute()
            if (characters.isEmpty()) _topHeadlines.value =
                TopHeadlinesState.Empty else _topHeadlines.value =
                TopHeadlinesState.Success(
                    characters
                )
        }
    }
}