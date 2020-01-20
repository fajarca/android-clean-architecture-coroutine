package io.fajarca.characters.presentation.detail

import androidx.lifecycle.*
import io.fajarca.characters.domain.CharacterDetail
import io.fajarca.characters.domain.usecase.GetCharactersDetailUseCase
import io.fajarca.core.vo.Result
import io.fajarca.core.network.HttpResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailViewModel (private val useCase: GetCharactersDetailUseCase) :
    ViewModel() {

    class Factory(private val useCase: GetCharactersDetailUseCase) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CharacterDetailViewModel::class.java)) {
                return CharacterDetailViewModel(useCase) as T
            }
            throw IllegalArgumentException("ViewModel not found.")
        }
    }


    private val _detail = MutableLiveData<Result<CharacterDetail>>()
    val characterDetail: LiveData<Result<CharacterDetail>>
        get() = _detail

    fun getCharacterDetail(characterId: Int) {
        _detail.postValue(Result.Loading)
        viewModelScope.launch {
            useCase.execute(characterId,
                onSuccess = { _detail.postValue(Result.Success(it)) },
                onError = { cause: HttpResult, code: Int?, errorMessage: String? ->
                    _detail.postValue(Result.Error(cause, code, errorMessage))
                })
        }
    }
}