package io.fajarca.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import io.fajarca.core.database.NewsEntity
import io.fajarca.home.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsDataSource(private val useCase : GetNewsUseCase, private val coroutineScope: CoroutineScope) : PageKeyedDataSource<Int, NewsEntity>()  {


    class Factory(private val useCase: GetNewsUseCase, private val coroutineScope: CoroutineScope) : DataSource.Factory<Int, NewsEntity>() {
        val sourceLiveData = MutableLiveData<NewsDataSource>()
        private lateinit var latestSource : NewsDataSource

        override fun create(): DataSource<Int, NewsEntity> {
            latestSource = NewsDataSource(useCase, coroutineScope)
            sourceLiveData.postValue(latestSource)
            return latestSource
        }
    }


    sealed class State {
        object Loading : State()
        object Empty : State()
        object Success: State()
        object Error: State()
    }
    
    private val _getNewsState = MutableLiveData<State>()
    val getNewsState : LiveData<State> = _getNewsState
    
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsEntity>) {
        coroutineScope.launch(CoroutineExceptionHandler { _, throwable -> setState(State.Error) }) {
            setState(State.Loading)

            val apiResult = useCase.execute(1, params.requestedLoadSize)
            callback.onResult(apiResult, null, 2)

            if (apiResult.isEmpty()) setState(State.Empty) else setState(State.Success)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsEntity>) {
        coroutineScope.launch(CoroutineExceptionHandler { _, throwable -> setState(State.Error) }) {
            setState(State.Loading)

            val apiResult = useCase.execute(params.key, params.requestedLoadSize)
            callback.onResult(apiResult, params.key + 1)

            if (apiResult.isEmpty()) setState(State.Empty) else setState(State.Success)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsEntity>) {

    }
    
    private fun setState(state : State) {
        _getNewsState.postValue(state)
    }
}