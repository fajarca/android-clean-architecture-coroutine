package io.fajarca.characters.domain.repository

import io.fajarca.core.vo.Result
import io.fajarca.core.database.CharacterEntity
import io.fajarca.characters.data.response.CharacterDto
import io.fajarca.characters.domain.MarvelCharacter

interface CharacterRepository {
    suspend fun getAllCharactersFromApi() : Result<CharacterDto>
    suspend fun getAllCharactersFromDb() : List<CharacterEntity>
    suspend fun getAllCharacter() : List<MarvelCharacter>
    suspend fun insertAllCharacter(characters : List<CharacterEntity>)
    suspend fun getCharacterDetail(characterId : Int) : Result<io.fajarca.characters.domain.CharacterDetail>
}


