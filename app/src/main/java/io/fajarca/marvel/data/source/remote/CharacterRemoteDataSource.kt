package io.fajarca.marvel.data.source.remote

import io.fajarca.marvel.data.service.ApiService
import io.fajarca.marvel.domain.model.common.Result
import io.fajarca.marvel.domain.model.response.CharacterDto
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource() {

    suspend fun getCharacters(dispatcher: CoroutineDispatcher): Result<CharacterDto> {
        return safeApiCall(dispatcher) { apiService.getCharacters() }
    }
}