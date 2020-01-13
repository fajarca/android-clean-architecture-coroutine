package io.fajarca.feature.data


import io.fajarca.core.common.Result
import io.fajarca.core.network.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CharacterDetailRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource() {

    suspend fun getCharacterDetail(characterId : Int, dispatcher: CoroutineDispatcher): Result<CharacterDetailDto> {
        return safeApiCall(dispatcher) { apiService.getCharacterDetail(characterId) }
    }
}