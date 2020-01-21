package io.fajarca.characters.data.source


import io.fajarca.characters.data.CharacterService
import io.fajarca.characters.data.response.CharacterDetailDto
import io.fajarca.characters.data.response.CharacterDto
import io.fajarca.core.vo.Result
import io.fajarca.core.network.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val characterService: CharacterService) :
    RemoteDataSource() {

    suspend fun getCharacters(dispatcher: CoroutineDispatcher): Result<CharacterDto> {
        return safeApiCall(dispatcher) { characterService.getCharacters() }
    }

    suspend fun getCharacterDetail(characterId : Int, dispatcher: CoroutineDispatcher): Result<CharacterDetailDto> {
        return safeApiCall(dispatcher) { characterService.getCharacterDetail(characterId) }
    }
}