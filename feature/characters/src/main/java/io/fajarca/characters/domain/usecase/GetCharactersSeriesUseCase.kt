package io.fajarca.characters.domain.usecase

import io.fajarca.characters.domain.entities.MarvelCharacterSeries
import io.fajarca.characters.domain.repository.CharacterRepository
import io.fajarca.core.network.HttpResult
import io.fajarca.core.vo.Result

class GetCharactersSeriesUseCase (private val repository: CharacterRepository)  {


    suspend fun execute(characterId: Int, onSuccess: (series : List<MarvelCharacterSeries>) -> Unit, onError: (cause: HttpResult, code : Int?, errorMessage : String?) -> Unit){
        val response = repository.getCharacterSeries(characterId)
        when (response) {
            is Result.Success -> onSuccess(response.data)
            is Result.Error -> onError(response.cause, response.code, response.errorMessage)
        }
    }

}