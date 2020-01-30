package io.fajarca.home.domain.repository

import io.fajarca.core.vo.Result
import io.fajarca.core.database.CharacterEntity
import io.fajarca.home.data.response.CharacterDto
import io.fajarca.home.domain.entities.MarvelCharacter

interface CharacterRepository {
    suspend fun getAllCharactersFromApi() : Result<CharacterDto>
    suspend fun getAllCharactersFromDb() : List<CharacterEntity>
    suspend fun getAllCharacter() : List<MarvelCharacter>
    suspend fun insertAllCharacter(characters : List<CharacterEntity>)
}


