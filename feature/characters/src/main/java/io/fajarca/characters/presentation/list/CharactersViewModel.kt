package io.fajarca.characters.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fajarca.characters.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.fajarca.characters.domain.MarvelCharacter

class CharactersViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private val _characters = MutableLiveData<CharacterState<List<MarvelCharacter>>>()
    val characters: LiveData<CharacterState<List<MarvelCharacter>>>
        get() = _characters

    sealed class CharacterState<out T> {
        object Empty : CharacterState<Nothing>()
        object Loading : CharacterState<Nothing>()
        data class Success<out T>(val data: T) : CharacterState<T>()
    }

    fun getAllCharacters() {
        _characters.postValue(CharacterState.Loading)
        viewModelScope.launch {
            val characters = getCharactersUseCase.execute()
            if (characters.isEmpty()) _characters.value =
                CharacterState.Empty else _characters.value =
                CharacterState.Success(
                    characters
                )
        }
    }
}