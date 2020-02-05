package io.fajarca.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.fajarca.core.vo.UiState
import io.fajarca.home.domain.entities.News
import io.fajarca.home.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsBoundaryCallback(private val country : String, private val getNewsUseCase: GetNewsUseCase, private val scope : CoroutineScope) : PagedList.BoundaryCallback<News>() {

    private val _newsState = MutableLiveData<UiState>()
    val newsState : LiveData<UiState> = _newsState

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
    private var lastRequestedPage = 1
    private val pageSize = 5

    private val _initialLoading = MutableLiveData<UiState>(UiState.Loading)
    val initialLoading : LiveData<UiState>
    get() = _initialLoading

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }
    override fun onItemAtEndLoaded(itemAtEnd: News) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        setState(UiState.Loading)

        scope.launch(CoroutineExceptionHandler { _, _ ->  onFetchNewsError()}) {

            getNewsUseCase.execute(country, lastRequestedPage, pageSize, onSuccessAction = {
                isRequestInProgress = false
                lastRequestedPage++
                setState(UiState.Success)
            })
            setState(UiState.Complete)
            setInitialLoadingState(UiState.Complete)
        }

    }

    private fun onFetchNewsError() {
        isRequestInProgress = false
        setState(UiState.Error)
    }

    private fun setState(result : UiState) {
        _newsState.postValue(result)
    }

    private fun setInitialLoadingState(state: UiState) {
        _initialLoading.postValue(state)
    }
}