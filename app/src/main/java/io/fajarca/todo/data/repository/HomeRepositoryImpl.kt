package io.fajarca.todo.data.repository

import io.fajarca.todo.data.mapper.CharactersMapper
import io.fajarca.todo.data.service.ApiService
import io.fajarca.todo.data.source.local.CharacterDao
import io.fajarca.todo.data.source.remote.safeApiCall
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.local.Character
import io.fajarca.todo.domain.repository.HomeRepository
import io.fajarca.todo.ui.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutinesDispatcherProvider,
    private val apiService: ApiService,
    private val mapper: CharactersMapper,
    private val dao: CharacterDao
) : HomeRepository {

    override suspend fun insertAllCharacter(characters: List<Character>) {
        dao.insertAll(characters)
    }

    override suspend fun getAllCharactersFromDb(): Flow<List<Character>> {
        return dao.findAll()
    }

    override suspend fun getAllCharacter(): Flow<List<Character>> {
        val apiResult = getAllCharactersFromApi()

        if (apiResult is Result.Success) {
            insertAllCharacter(apiResult.data)
        }

        return dao.findAll()
    }


    override suspend fun getAllCharactersFromApi(): Result<List<Character>> {
        return safeApiCall(dispatcher.io) { mapper.mapToCharacterEntity(apiService.getCharacters()) }
    }


}