package io.fajarca.home.data.source


import io.fajarca.home.data.NewsService
import io.fajarca.home.data.response.CharacterDetailDto
import io.fajarca.home.data.response.CharacterDto
import io.fajarca.home.data.response.CharacterSeriesDto
import io.fajarca.core.network.RemoteDataSource
import io.fajarca.core.vo.Result
import kotlinx.coroutines.CoroutineDispatcher

class NewsRemoteDataSource (private val characterService: NewsService) :
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