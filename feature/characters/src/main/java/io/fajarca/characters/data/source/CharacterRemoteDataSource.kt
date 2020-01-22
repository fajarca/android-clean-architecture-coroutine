package io.fajarca.characters.data.source


import io.fajarca.characters.data.CharacterService
import io.fajarca.characters.data.response.CharacterDetailDto
import io.fajarca.characters.data.response.CharacterDto
import io.fajarca.characters.data.response.CharacterSeriesDto
import io.fajarca.core.network.RemoteDataSource
import io.fajarca.core.vo.Result
import kotlinx.coroutines.CoroutineDispatcher

class CharacterRemoteDataSource (private val characterService: CharacterService) :
    RemoteDataSource() {

    suspend fun getCharacters(dispatcher: CoroutineDispatcher): Result<CharacterDto> {
        return safeApiCall(dispatcher) { characterService.getCharacters() }
    }

    suspend fun getCharacterDetail(characterId : Int, dispatcher: CoroutineDispatcher): Result<CharacterDetailDto> {
        return safeApiCall(dispatcher) { characterService.getCharacterDetail(characterId) }
    }
    suspend fun getCharacterSeries(characterId : Int, dispatcher: CoroutineDispatcher): Result<CharacterSeriesDto> {
        return safeApiCall(dispatcher) { characterService.getCharacterSeries(characterId) }
    }
}