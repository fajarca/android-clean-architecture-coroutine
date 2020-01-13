package io.fajarca.feature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fajarca.core.common.Result
import io.fajarca.core.network.HttpResult
import io.fajarca.feature.data.CharacterDetailDto
import io.fajarca.feature.domain.CharacterDetail
import io.fajarca.feature.domain.usecase.GetCharactersDetailUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(private val useCase: GetCharactersDetailUseCase) :
    ViewModel() {

    private val _detail = MutableLiveData<Result<CharacterDetailDto>>()
    val characters: LiveData<Result<CharacterDetailDto>>
        get() = _detail

    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch {
            useCase.execute(characterId,
                onLoading = { _detail.postValue(Result.Loading) },
                onSuccess = { _detail.postValue(Result.Success(it)) },
                onError = { cause: HttpResult, code: Int?, errorMessage: String? ->
                    _detail.postValue(Result.Error(cause, code, errorMessage))
                })
        }
    }
}