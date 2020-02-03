package io.fajarca.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.fajarca.home.domain.entities.News
import io.fajarca.home.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsBoundaryCallback(private val getNewsUseCase: GetNewsUseCase, private val scope : CoroutineScope) : PagedList.BoundaryCallback<News>() {

    private val _newsState = MutableLiveData<State>()
    val newsState : LiveData<State> = _newsState

    sealed class State {
        object Loading : State()
        object Success: State()
        object Error: State()
    }

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
    private var lastRequestedPage = 1
    private val pageSize = 5

    override fun onZeroItemsLoaded() {
        Timber.v("OnZeroItemLoaded")
        requestAndSaveData()
    }
    override fun onItemAtEndLoaded(itemAtEnd: News) {
        Timber.v("onItemAtEndLoaded")
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        setState(State.Loading)

        scope.launch(CoroutineExceptionHandler { _, _ ->  onFetchNewsError()}) {

            getNewsUseCase.execute(lastRequestedPage, pageSize, onSuccess = {
                isRequestInProgress = false
                lastRequestedPage++
                setState(State.Success)
            })
        }

    }

    private fun onFetchNewsError() {
        isRequestInProgress = false
        setState(State.Error)
    }

    private fun setState(result : State) {
        _newsState.postValue(result)
    }
}