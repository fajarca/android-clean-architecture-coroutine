package io.fajarca.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.fajarca.core.database.NewsEntity
import io.fajarca.core.vo.Result
import io.fajarca.home.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsBoundaryCallback(private val getNewsUseCase: GetNewsUseCase, private val scope : CoroutineScope) : PagedList.BoundaryCallback<NewsEntity>() {

    private val _newsState = MutableLiveData<NewsState>()

    sealed class NewsState {
        object LOADING : NewsState()
        object SUCCESS: NewsState()
        object ERROR: NewsState()
    }

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
    private var lastRequestedPage = 1
    private val pageSize = 25

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
        Timber.v("OnZero item loaded")
    }
    override fun onItemAtEndLoaded(itemAtEnd: NewsEntity) {
        requestAndSaveData()
        Timber.v("OnItem at end loaded")
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        setResult(NewsState.LOADING)

        scope.launch {
            getNewsUseCase.execute(lastRequestedPage, pageSize, onSuccess = {
                isRequestInProgress = false
                lastRequestedPage++
                setResult(NewsState.SUCCESS)
            }, onError = {
                isRequestInProgress = false
                setResult(NewsState.ERROR)
            })
        }



    }

    private fun setResult(result : NewsState) {
        _newsState.postValue(result)
    }
}