package io.fajarca.home.domain.repository

import io.fajarca.core.common.Result
import io.fajarca.core.database.CharacterEntity
import io.fajarca.home.data.CharacterDto
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getAllCharactersFromApi() : Result<CharacterDto>
    suspend fun getAllCharactersFromDb() : Flow<List<CharacterEntity>>
    suspend fun getAllCharacter() : Flow<List<CharacterEntity>>
    suspend fun insertAllCharacter(characters : List<CharacterEntity>)
}


