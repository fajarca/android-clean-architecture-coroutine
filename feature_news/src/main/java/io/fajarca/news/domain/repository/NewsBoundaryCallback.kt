package io.fajarca.news.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.fajarca.core.network.HttpResult
import io.fajarca.core.vo.Result
import io.fajarca.news.domain.entities.News
import io.fajarca.news.domain.usecase.InsertNewsUseCase
import io.fajarca.news.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsBoundaryCallback(private val country : String?, private val category : String?, private val insertNewsUseCase: InsertNewsUseCase, private val scope : CoroutineScope) : PagedList.BoundaryCallback<News>() {

    private val _newsState = MutableLiveData<Result<List<News>>>()
    val newsState : LiveData<Result<List<News>>> = _newsState

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
    private var lastRequestedPage = 1

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }
    override fun onItemAtEndLoaded(itemAtEnd: News) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        setState(Result.Loading)

        scope.launch {

            insertNewsUseCase(country,
                category,
                lastRequestedPage,
                HomeViewModel.PAGE_SIZE,
                onSuccessAction = {onFetchSuccess()} ,
                onErrorAction = { cause, code, errorMessage ->  onFetchNewsError(cause, code, errorMessage)})
        }

    }

    private fun onFetchSuccess() {
        isRequestInProgress = false
        lastRequestedPage++
        setState(Result.Success(emptyList()))
    }
    private fun onFetchNewsError(cause : HttpResult, code : Int, errorMessage : String) {
        isRequestInProgress = false
        setState(Result.Error(cause, code, errorMessage))
    }

    private fun setState(result : Result<List<News>>) {
        _newsState.postValue(result)
    }

}