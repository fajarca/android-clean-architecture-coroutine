package io.fajarca.characters.presentation.list

import androidx.lifecycle.*
import io.fajarca.characters.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.fajarca.characters.domain.MarvelCharacter
import io.fajarca.characters.domain.usecase.GetCharactersDetailUseCase
import io.fajarca.characters.presentation.detail.CharacterDetailViewModel

class CharactersViewModel ( private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    class Factory @Inject constructor(
        private val useCase: GetCharactersUseCase
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
                return CharactersViewModel(useCase) as T
            }
            throw IllegalArgumentException("ViewModel not found")
        }
    }

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