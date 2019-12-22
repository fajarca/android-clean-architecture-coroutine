package io.fajarca.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fajarca.todo.data.RemoteHandler
import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.response.CharacterDto
import io.fajarca.todo.domain.usecase.GetCharactersUseCase
import io.fajarca.todo.ui.CoroutinesDispatcherProvider
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val dispatcher: CoroutinesDispatcherProvider,
    private val remoteHandler: RemoteHandler
) : ViewModel() {

    private val _characters = MutableLiveData<Result<List<Character>>>()
    val characters: LiveData<Result<List<Character>>>
        get() = _characters

    fun getAllCharacters() {
        _characters.postValue(Result.loading())
        viewModelScope.launch(dispatcher.io) {
            try {
                _characters.postValue(Result.success(getCharactersUseCase.execute()))
            } catch (e : Exception) {
                _characters.postValue(Result.error(remoteHandler.map(e)))
            }

        }
    }
}