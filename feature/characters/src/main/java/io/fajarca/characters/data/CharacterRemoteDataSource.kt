package io.fajarca.characters.data


import io.fajarca.core.vo.Result
import io.fajarca.core.network.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource() {

    suspend fun getCharacters(dispatcher: CoroutineDispatcher): Result<CharacterDto> {
        return safeApiCall(dispatcher) { apiService.getCharacters() }
    }
}