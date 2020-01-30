package io.fajarca.home.data

import io.fajarca.core.database.CharacterDao
import io.fajarca.core.database.CharacterEntity
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.vo.Result
import io.fajarca.home.data.mapper.CharactersMapper
import io.fajarca.home.data.response.CharacterDto
import io.fajarca.home.data.source.NewsRemoteDataSource
import io.fajarca.home.domain.entities.MarvelCharacter
import io.fajarca.home.domain.repository.CharacterRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper: CharactersMapper,
    private val dao: CharacterDao,
    private val remoteDataSource: NewsRemoteDataSource
) : CharacterRepository {


    override suspend fun insertAllCharacter(characters: List<CharacterEntity>) {
        dao.insertAll(characters)
    }

    override suspend fun getAllCharactersFromDb(): List<CharacterEntity> {
        return withContext(dispatcher.io) {
            dao.findAll()
        }
    }

    override suspend fun getAllCharacter(): List<MarvelCharacter> {
        val apiResult = getAllCharactersFromApi()

        if (apiResult is Result.Success) {
            insertAllCharacter(mapper.map(apiResult.data))
        }

        return mapper.mapToDomain(getAllCharactersFromDb())
    }


    override suspend fun getAllCharactersFromApi(): Result<CharacterDto> {
        return remoteDataSource.getCharacters(dispatcher.io)
    }


}