package io.fajarca.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.response.CharacterDto
import io.fajarca.todo.domain.usecase.GetCharactersUseCase
import io.fajarca.todo.ui.CoroutinesDispatcherProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {

    private val _characters = MutableLiveData<Result<CharacterDto>>()
    val characters: LiveData<Result<CharacterDto>>
        get() = _characters

    fun getAllCharacters() {
        _characters.postValue(Result.loading())
        viewModelScope.launch(dispatcher.io) {
            _characters.postValue(getCharactersUseCase.execute())
        }
    }
}