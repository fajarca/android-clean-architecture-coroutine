package io.fajarca.todo.ui.home

import androidx.lifecycle.*
import io.fajarca.todo.data.local.entity.Todo
import io.fajarca.todo.data.remote.response.CharactersResponse
import io.fajarca.todo.data.remote.response.NowPlaying
import io.fajarca.todo.data.remote.response.NowPlayingResponse
import io.fajarca.todo.data.repository.HomeRepository
import io.fajarca.todo.util.CoroutinesDispatcherProvider
import io.fajarca.todo.vo.Result
import io.fajarca.todo.vo.ValidationResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {


    private val _characters = MutableLiveData<Result<CharactersResponse>>()
    val characters: LiveData<Result<CharactersResponse>>
        get() = _characters



    fun getAllCharacters() {
        _characters.postValue(Result.loading(null))
        viewModelScope.launch(dispatcher.io) {
            _characters.postValue(repository.getAllCharacters())
        }
    }
}