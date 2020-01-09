package io.fajarca.home.domain.repository

import io.fajarca.core.common.Result
import kotlinx.coroutines.flow.Flow
import io.fajarca.core.database.Character
import io.fajarca.home.data.CharacterDto

interface CharacterRepository {
    suspend fun getAllCharactersFromApi() : Result<CharacterDto>
    suspend fun getAllCharactersFromDb() : Flow<List<Character>>
    suspend fun getAllCharacter() : Flow<List<Character>>
    suspend fun insertAllCharacter(characters : List<Character>)
}


