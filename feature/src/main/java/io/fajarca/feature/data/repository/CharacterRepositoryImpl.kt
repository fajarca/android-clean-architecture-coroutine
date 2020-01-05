package io.fajarca.feature.data.repository

import io.fajarca.core.database.CharacterDao
import io.fajarca.feature.data.mapper.CharactersMapper
import io.fajarca.feature.data.source.remote.CharacterRemoteDataSource
import io.fajarca.feature.domain.model.common.Result
import io.fajarca.feature.domain.repository.CharacterRepository
import io.fajarca.feature.data.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import io.fajarca.core.database.Character
import io.fajarca.core.network.response.CharacterDto

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