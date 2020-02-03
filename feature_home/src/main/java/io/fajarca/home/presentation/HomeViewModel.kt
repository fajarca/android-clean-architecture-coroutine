package io.fajarca.home.presentation

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.fajarca.core.database.NewsEntity
import io.fajarca.home.presentation.mapper.NewsPresentationMapper
import io.fajarca.home.domain.entities.News
import io.fajarca.home.domain.usecase.GetNewsUseCase
import io.fajarca.home.domain.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel (private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase, private val getNewsUseCase: GetNewsUseCase, private val mapper: NewsPresentationMapper) : ViewModel() {

    class Factory @Inject constructor(
        private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
        private val getNewsUseCase: GetNewsUseCase,
        private val mapper: NewsPresentationMapper
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(
                    getTopHeadlinesUseCase,
                    getNewsUseCase,
                    mapper
                ) as T
            }
            throw IllegalArgumentException("ViewModel not found")
        }
    }

    companion object {
        const val PAGE_SIZE = 10
    }

    private val _news = MutableLiveData<TopHeadlinesState<List<News>>>()
    val news: LiveData<TopHeadlinesState<List<News>>>
        get() = _news

    private val _topHeadlines = MutableLiveData<TopHeadlinesState<List<News>>>()
    val topHeadlines: LiveData<TopHeadlinesState<List<News>>>
        get() = _topHeadlines

    sealed class TopHeadlinesState<out T> {
        object Empty : TopHeadlinesState<Nothing>()
        object Loading : TopHeadlinesState<Nothing>()
        data class Success<out T>(val data: T) : TopHeadlinesState<T>()
    }

    /*private val factory = getNewsUseCase.fetchFromDb()
    private val boundaryCallback = NewsBoundaryCallback(getNewsUseCase, viewModelScope)

    val newsSourceState = boundaryCallback.newsState
    val newsSource = LivePagedListBuilder(factory, PAGE_SIZE)
        .setBoundaryCallback(boundaryCallback)
        .build()*/

    private val newsDataSourceFactory = NewsDataSource.Factory(getNewsUseCase, viewModelScope)
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(2 * PAGE_SIZE)
        .setPageSize(PAGE_SIZE)
        .build()

    val newsSource : LiveData<PagedList<NewsEntity>> = LivePagedListBuilder(newsDataSourceFactory, config).build()
    val newsSourceState = Transformations.switchMap(newsDataSourceFactory.sourceLiveData) {it.getNewsState}

    fun getTopHeadlines() {
        _topHeadlines.value = TopHeadlinesState.Loading
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