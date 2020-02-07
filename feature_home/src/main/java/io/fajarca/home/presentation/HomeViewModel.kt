package io.fajarca.home.presentation

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.fajarca.home.presentation.mapper.NewsPresentationMapper
import io.fajarca.home.domain.entities.News
import io.fajarca.home.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel (private val getNewsUseCase: GetNewsUseCase, private val mapper: NewsPresentationMapper) : ViewModel() {

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

    private val _category = MutableLiveData<String>()

    val news : LiveData<PagedList<News>> = Transformations.switchMap(_category) {
        search(it)
    }

    fun setData(country : String, category : String?) {
        _category.postValue(category)
    }

    private fun search(category: String?): LiveData<PagedList<News>> {
         val factory = getNewsUseCase.getNewsFactory().map { mapper.map(it) }
        val boundaryCallback = NewsBoundaryCallback("id", category, getNewsUseCase, viewModelScope)

        val newsSourceState = boundaryCallback.newsState
        val initialLoadingState = boundaryCallback.initialLoading
        val newsSource = LivePagedListBuilder(factory, PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return newsSource
    }
}