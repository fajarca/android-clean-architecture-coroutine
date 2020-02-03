package io.fajarca.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.fajarca.core.database.NewsEntity
import io.fajarca.home.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsBoundaryCallback(private val getNewsUseCase: GetNewsUseCase, private val scope : CoroutineScope) : PagedList.BoundaryCallback<NewsEntity>() {

    private val _newsState = MutableLiveData<NewsState>()
    val newsState : LiveData<NewsState> = _newsState

    sealed class NewsState {
        object Loading : NewsState()
        object Success: NewsState()
        object Error: NewsState()
    }

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
    private var lastRequestedPage = 1
    private val pageSize = 5

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }
    override fun onItemAtEndLoaded(itemAtEnd: NewsEntity) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
      /*  if (isRequestInProgress) return

        setResult(NewsState.Loading)

        scope.launch {
            getNewsUseCase.execute(lastRequestedPage, pageSize, onSuccess = {
                isRequestInProgress = false
                lastRequestedPage++
                setResult(NewsState.Success)
            }, onError = {
                isRequestInProgress = false
                setResult(NewsState.Error)
            })
        }*/



    }

    private fun setResult(result : NewsState) {
        _newsState.postValue(result)
    }
}