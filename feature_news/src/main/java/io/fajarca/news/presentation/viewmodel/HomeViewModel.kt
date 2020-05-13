package io.fajarca.news.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.fajarca.core.vo.Result
import io.fajarca.news.domain.entities.News
import io.fajarca.news.domain.entities.SearchQuery
import io.fajarca.news.domain.repository.NewsBoundaryCallback
import io.fajarca.news.domain.usecase.GetNewsUseCase
import io.fajarca.news.domain.usecase.InsertNewsUseCase
import io.fajarca.news.domain.usecase.RefreshNewsUseCase
import io.fajarca.news.presentation.mapper.NewsPresentationMapper
import io.fajarca.news.presentation.model.SearchResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val insertNewsUseCase: InsertNewsUseCase,
    private val refreshNewsUseCase: RefreshNewsUseCase,
    private val mapper: NewsPresentationMapper
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 10
    }

    private val _query = MutableLiveData<SearchQuery>()

    private val searchResult = Transformations.map(_query) {
        search(it.country, it.category)
    }

    val news = Transformations.switchMap(searchResult) { it.news }
    val searchState : LiveData<Result<List<News>>> = Transformations.switchMap(searchResult) { it.searchState }

    private val _refreshNews = MutableLiveData<Result<List<News>>>()
    val refreshNews: LiveData<Result<List<News>>>
        get() = _refreshNews

    fun setSearchQuery(country: String?, category: String?) {
        _query.postValue(SearchQuery(country, category))
    }

    private fun search(country: String?, category: String?): SearchResult = runBlocking {
        val factory = getNewsUseCase(country, category).map { mapper.map(it, Locale.getDefault()) }
        val boundaryCallback = NewsBoundaryCallback(country, category, insertNewsUseCase, viewModelScope)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(2 * PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()

        val newsSourceState = boundaryCallback.newsState
        val newsSource = LivePagedListBuilder(factory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()

        SearchResult(newsSourceState, newsSource)
    }

    fun refreshNews(country: String?, category: String?) {
        _refreshNews.value = Result.Loading
        viewModelScope.launch {
            val result = refreshNewsUseCase(country, category)
            when(result) {
                is Result.Success -> {
                    _refreshNews.value = Result.Success(emptyList())
                }
                is Result.Error -> {
                    _refreshNews.value = Result.Error(result.cause, result.code, result.errorMessage)
                }
            }
        }
    }


}