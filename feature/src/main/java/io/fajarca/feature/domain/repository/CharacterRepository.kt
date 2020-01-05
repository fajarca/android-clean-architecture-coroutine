package io.fajarca.feature.domain.repository

import io.fajarca.core.network.response.CharacterDto
import io.fajarca.feature.domain.model.common.Result
import kotlinx.coroutines.flow.Flow
import io.fajarca.core.database.Character

interface CharacterRepository {
    suspend fun getAllCharactersFromApi() : Result<CharacterDto>
    suspend fun getAllCharactersFromDb() : Flow<List<Character>>
    suspend fun getAllCharacter() : Flow<List<Character>>
    suspend fun insertAllCharacter(characters : List<Character>)
}


