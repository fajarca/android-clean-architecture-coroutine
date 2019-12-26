package io.fajarca.todo.data.source.remote

import io.fajarca.todo.data.service.ApiService
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.response.CharacterDto
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource() {

    suspend fun getCharacters(dispatcher: CoroutineDispatcher): Result<CharacterDto> {
        return safeApiCall(dispatcher) { apiService.getCharacters() }
    }
}