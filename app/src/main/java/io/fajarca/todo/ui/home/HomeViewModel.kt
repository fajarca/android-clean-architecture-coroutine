package io.fajarca.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fajarca.todo.domain.model.response.CharactersResponse
import io.fajarca.todo.domain.usecase.GetCharactersUseCase
import io.fajarca.todo.ui.CoroutinesDispatcherProvider
import io.fajarca.todo.domain.model.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _characters = MutableLiveData<Result<CharactersResponse>>()
    val characters: LiveData<Result<CharactersResponse>>
        get() = _characters

    fun getAllCharacters() {
        _characters.postValue(Result.loading(null))
        viewModelScope.launch(dispatcher.io) {
            _characters.postValue(getCharactersUseCase.execute())
        }
    }
}