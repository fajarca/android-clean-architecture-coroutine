package io.fajarca.marvel.domain.repository

import io.fajarca.marvel.domain.model.common.Result
import io.fajarca.marvel.domain.model.local.Character
import io.fajarca.marvel.domain.model.response.CharacterDto
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getAllCharactersFromApi() : Result<CharacterDto>
    suspend fun getAllCharactersFromDb() : Flow<List<Character>>
    suspend fun getAllCharacter() : Flow<List<Character>>
    suspend fun insertAllCharacter(characters : List<Character>)
}


