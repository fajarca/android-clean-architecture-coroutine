package io.fajarca.todo.ui.home

import androidx.lifecycle.*
import io.fajarca.todo.data.local.entity.Todo
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

    private val _insertTodo = MutableLiveData<ValidationResult>()
    val insertTodo: LiveData<ValidationResult>
        get() = _insertTodo

    private val _todo = MutableLiveData<Result<List<Todo>>>()
    val todo: LiveData<Result<List<Todo>>>
        get() = _todo

    private val _nowPlaying = MutableLiveData<Result<NowPlayingResponse>>()
    val nowPlaying: LiveData<Result<NowPlayingResponse>>
        get() = _nowPlaying


    fun validateInput(title: String, description: String) {

        if (!isTitleValid(title)) {
            _insertTodo.value = ValidationResult.INVALID("Title cannot be empty")
            return
        }

        if (!isDescriptionValid(description)) {
            _insertTodo.value = ValidationResult.INVALID("Description cannot be empty")
            return
        }

        if (isTitleValid(title) && isDescriptionValid(description)) {
            insertTodo(title, description)
        }
    }

    fun isTitleValid(title: String): Boolean {
        if (!title.isNullOrEmpty()) {
            return true
        }
        return false
    }

    fun isDescriptionValid(description: String): Boolean {
        if (!description.isNullOrEmpty()) {
            return true
        }
        return false
    }

    fun insertTodo(title: String, description: String) {
        viewModelScope.launch(dispatcher.io) {
            repository.insert(Todo(title, description, false))
        }
    }

    fun getTodo() {
        viewModelScope.launch(dispatcher.io) {
            repository.findAll()
                .onStart {
                    _todo.postValue(Result.loading(null))
                }
                .collect {
                    _todo.postValue(Result.success(it))
                }
        }
    }

    fun deleteTodo(id : Long) {
        viewModelScope.launch(dispatcher.io) {
            repository.deleteById(id)
        }
    }

    fun getAllNowPlaying() {
        _nowPlaying.postValue(Result.loading(null))
        viewModelScope.launch(dispatcher.io) {
            _nowPlaying.postValue(repository.getAllNowPlaying())
        }
    }
}