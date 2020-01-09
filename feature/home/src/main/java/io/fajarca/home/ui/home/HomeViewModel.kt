package io.fajarca.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fajarca.home.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.fajarca.core.database.Character

class HomeViewModel (private val getCharactersUseCase: GetCharactersUseCase) : ViewModel() {

    private val _characters = MutableLiveData<Result<List<Character>>>()
    val characters: LiveData<Result<List<Character>>>
        get() = _characters

    sealed class Result<out T> {
        object Empty : Result<Nothing>()
        object Loading : Result<Nothing>()
        data class Success<out T>(val data : T): Result<T>()
        data class Error(val throwable: Throwable) : Result<Nothing>()
    }

    fun getAllCharacters() {
        _characters.postValue(Result.Loading)
        viewModelScope.launch {
            getCharactersUseCase.execute()
                .catch {
                    _characters.postValue(Result.Error(it))
                }
                .collect {
                    if (it.isEmpty()) {
                        _characters.postValue(Result.Empty)
                    } else {
                        _characters.postValue(Result.Success(it))
                    }
                }
        }
    }
}