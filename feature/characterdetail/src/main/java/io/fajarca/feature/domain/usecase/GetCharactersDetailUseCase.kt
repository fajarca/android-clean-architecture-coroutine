package io.fajarca.feature.domain.usecase

import io.fajarca.core.common.Result
import io.fajarca.core.network.HttpResult
import io.fajarca.feature.data.CharacterDetailDto
import io.fajarca.feature.domain.repository.CharacterDetailRepository
import javax.inject.Inject

class GetCharactersDetailUseCase @Inject constructor(private val repository: CharacterDetailRepository)  {


    suspend fun execute(
        characterId: Int,
        onLoading: () -> Unit,
        onSuccess: (detail : CharacterDetailDto) -> Unit,
        onError: (cause: HttpResult, code : Int?, errorMessage : String?) -> Unit
    ){
        val response = repository.getCharacterDetail(characterId)
        when (response) {
            is Result.Loading -> onLoading()
            is Result.Success -> onSuccess(response.data)
            is Result.Error -> onError(response.cause, response.code, response.errorMessage)
        }
    }

}