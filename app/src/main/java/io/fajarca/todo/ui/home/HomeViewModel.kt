package io.fajarca.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _characters = MutableLiveData<Result<List<Character>>>()
    val characters: LiveData<Result<List<Character>>>
        get() = _characters

    fun getAllCharacters() {
        _characters.postValue(Result.Loading)
        viewModelScope.launch {
            _characters.postValue(getCharactersUseCase.execute())
        }
    }
}