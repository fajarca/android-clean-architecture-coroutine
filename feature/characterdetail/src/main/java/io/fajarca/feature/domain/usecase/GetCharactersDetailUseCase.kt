package io.fajarca.feature.domain.usecase

import io.fajarca.core.vo.Result
import io.fajarca.core.network.HttpResult
import io.fajarca.feature.domain.CharacterDetail
import io.fajarca.feature.domain.repository.CharacterDetailRepository
import javax.inject.Inject

class GetCharactersDetailUseCase @Inject constructor(private val repository: CharacterDetailRepository)  {


    suspend fun execute(characterId: Int, onSuccess: (detail : CharacterDetail) -> Unit, onError: (cause: HttpResult, code : Int?, errorMessage : String?) -> Unit){
        val response = repository.getCharacterDetail(characterId)
        when (response) {
            is Result.Success -> onSuccess(response.data)
            is Result.Error -> onError(response.cause, response.code, response.errorMessage)
        }
    }

}