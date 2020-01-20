package io.fajarca.characters.data

import io.fajarca.characters.data.mapper.CharacterDetailMapper
import io.fajarca.characters.data.mapper.CharactersMapper
import io.fajarca.characters.data.response.CharacterDto
import io.fajarca.characters.data.source.CharacterRemoteDataSource
import io.fajarca.characters.domain.CharacterDetail
import io.fajarca.core.dispatcher.CoroutineDispatcherProvider
import io.fajarca.core.vo.Result
import io.fajarca.core.database.CharacterDao
import io.fajarca.core.database.CharacterEntity
import io.fajarca.characters.domain.MarvelCharacter
import io.fajarca.characters.domain.repository.CharacterRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper: CharactersMapper,
    private val detailMapper : CharacterDetailMapper,
    private val dao: CharacterDao,
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override suspend fun getCharacterDetail(characterId: Int): Result<CharacterDetail> {
        val apiResult = remoteDataSource.getCharacterDetail(characterId, dispatcher.io)
        return detailMapper.map(apiResult)
    }

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