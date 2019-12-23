package io.fajarca.todo.data.repository

import io.fajarca.todo.data.source.remote.safeApiCall
import io.fajarca.todo.data.mapper.CharactersMapper
import io.fajarca.todo.data.service.ApiService
import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.repository.HomeRepository
import io.fajarca.todo.ui.CoroutinesDispatcherProvider
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutinesDispatcherProvider,
    private val apiService: ApiService,
    private val mapper: CharactersMapper
) :
    HomeRepository {

    override suspend fun getAllCharacters(): Result<List<Character>> {
        return safeApiCall(dispatcher.io) { mapper.map(apiService.getCharacters()) }
    }

}