package io.fajarca.news.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.fajarca.news.presentation.mapper.NewsPresentationMapper
import io.fajarca.news.domain.entities.SearchQuery
import io.fajarca.news.domain.repository.NewsBoundaryCallback
import io.fajarca.news.domain.usecase.GetNewsUseCase
import io.fajarca.news.presentation.model.SearchResult
import java.util.*
import javax.inject.Inject

class HomeViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val mapper: NewsPresentationMapper
) : ViewModel() {

    class Factory @Inject constructor(
        private val getNewsUseCase: GetNewsUseCase,
        private val mapper: NewsPresentationMapper
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(
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

    private val _query = MutableLiveData<SearchQuery>()

    private val searchResult = Transformations.map(_query) {
        search(it.country, it.category)
    }

    val news = Transformations.switchMap(searchResult) { it.news }
    val initialLoadingState = Transformations.switchMap(searchResult) { it.initialLoadingState }
    val searchState = Transformations.switchMap(searchResult) { it.searchState }

    fun setSearchQuery(country: String?, category: String?) {
        _query.postValue(SearchQuery(country, category))
    }

    private fun search(country: String?, category: String?): SearchResult {
        val factory = getNewsUseCase.getNewsFactory(country, category).map { mapper.map(it, Locale.getDefault()) }
        val boundaryCallback = NewsBoundaryCallback(country, category, getNewsUseCase, viewModelScope)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(2 * PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()

        val newsSourceState = boundaryCallback.newsState
        val initialLoadingState = boundaryCallback.initialLoading
        val newsSource = LivePagedListBuilder(factory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return SearchResult(
            newsSourceState,
            initialLoadingState,
            newsSource
        )
    }
}