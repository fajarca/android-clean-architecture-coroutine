package io.fajarca.feature.data.source.remote

import io.fajarca.core.network.response.CharacterDto
import io.fajarca.core.network.service.ApiService
import io.fajarca.feature.domain.model.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource() {

    suspend fun getCharacters(dispatcher: CoroutineDispatcher): Result<CharacterDto> {
        return safeApiCall(dispatcher) { apiService.getCharacters() }
    }
}