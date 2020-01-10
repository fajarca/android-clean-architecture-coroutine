package io.fajarca.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fajarca.home.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.fajarca.core.database.CharacterEntity

class HomeViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private val _characters = MutableLiveData<Result<List<CharacterEntity>>>()
    val characters: LiveData<Result<List<CharacterEntity>>>
        get() = _characters

    sealed class Result<out T> {
        object Empty : Result<Nothing>()
        object Loading : Result<Nothing>()
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val throwable: Throwable) : Result<Nothing>()
    }

    fun getAllCharacters() {
        _characters.postValue(Result.Loading)
        viewModelScope.launch {
            getCharactersUseCase.execute(
                { _characters.value = Result.Success(it) },
                { throwable ->  _characters.value = Result.Error(throwable) },
                { _characters.value = Result.Empty }
            )
        }
    }
}