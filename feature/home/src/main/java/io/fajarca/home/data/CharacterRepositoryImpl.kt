package io.fajarca.home.data

import io.fajarca.core.common.CoroutineDispatcherProvider
import io.fajarca.core.common.Result
import io.fajarca.core.database.CharacterDao
import io.fajarca.core.database.CharacterEntity
import io.fajarca.home.domain.MarvelCharacter
import io.fajarca.home.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper: CharactersMapper,
    private val dao: CharacterDao,
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override suspend fun insertAllCharacter(characters: List<CharacterEntity>) {
        dao.insertAll(characters)
    }

    override suspend fun getAllCharactersFromDb(): Flow<List<CharacterEntity>> {
        return dao.findAll()
    }

    override suspend fun getAllCharacter(): Flow<List<CharacterEntity>> {
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