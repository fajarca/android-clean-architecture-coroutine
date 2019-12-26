package io.fajarca.marvel.data.repository

import io.fajarca.marvel.data.mapper.CharactersMapper
import io.fajarca.marvel.data.source.local.CharacterDao
import io.fajarca.marvel.data.source.remote.CharacterRemoteDataSource
import io.fajarca.marvel.domain.model.common.Result
import io.fajarca.marvel.domain.model.local.Character
import io.fajarca.marvel.domain.model.response.CharacterDto
import io.fajarca.marvel.domain.repository.CharacterRepository
import io.fajarca.marvel.ui.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutinesDispatcherProvider,
    private val mapper: CharactersMapper,
    private val dao: CharacterDao,
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override suspend fun insertAllCharacter(characters: List<Character>) {
        dao.insertAll(characters)
    }

    override suspend fun getAllCharactersFromDb(): Flow<List<Character>> {
        return dao.findAll()
    }

    override suspend fun getAllCharacter(): Flow<List<Character>> {
        val apiResult = getAllCharactersFromApi()

        if (apiResult is Result.Success) {
            insertAllCharacter(mapper.map(apiResult.data))
        }

        return dao.findAll()
    }


    override suspend fun getAllCharactersFromApi(): Result<CharacterDto> {
        return remoteDataSource.getCharacters(dispatcher.io)
    }


}