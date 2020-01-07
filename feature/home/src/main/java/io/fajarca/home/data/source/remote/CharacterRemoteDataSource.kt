package io.fajarca.home.data.source.remote

import io.fajarca.core.network.service.ApiService
import io.fajarca.core.network.CharacterDto
import io.fajarca.core.common.Result
import io.fajarca.core.network.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource() {

    suspend fun getCharacters(dispatcher: CoroutineDispatcher): Result<CharacterDto> {
        return safeApiCall(dispatcher) { apiService.getCharacters() }
    }
}