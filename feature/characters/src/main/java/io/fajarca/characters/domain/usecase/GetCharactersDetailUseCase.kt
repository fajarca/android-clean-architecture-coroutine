package io.fajarca.characters.domain.usecase

import io.fajarca.characters.domain.repository.CharacterRepository
import io.fajarca.core.network.HttpResult
import io.fajarca.core.vo.Result
import javax.inject.Inject

class GetCharactersDetailUseCase @Inject constructor(private val repository: CharacterRepository)  {


    suspend fun execute(characterId: Int, onSuccess: (detail : io.fajarca.characters.domain.CharacterDetail) -> Unit, onError: (cause: HttpResult, code : Int?, errorMessage : String?) -> Unit){
        val response = repository.getCharacterDetail(characterId)
        when (response) {
            is Result.Success -> onSuccess(response.data)
            is Result.Error -> onError(response.cause, response.code, response.errorMessage)
        }
    }

}