package io.fajarca.characters.domain.repository

import io.fajarca.core.vo.Result
import io.fajarca.core.database.CharacterEntity
import io.fajarca.characters.data.response.CharacterDto
import io.fajarca.characters.domain.entities.MarvelCharacterDetail
import io.fajarca.characters.domain.entities.MarvelCharacter
import io.fajarca.characters.domain.entities.MarvelCharacterSeries

interface CharacterRepository {
    suspend fun getAllCharactersFromApi() : Result<CharacterDto>
    suspend fun getAllCharactersFromDb() : List<CharacterEntity>
    suspend fun getAllCharacter() : List<MarvelCharacter>
    suspend fun insertAllCharacter(characters : List<CharacterEntity>)
    suspend fun getCharacterDetail(characterId : Int) : Result<MarvelCharacterDetail>
    suspend fun getCharacterSeries(characterId: Int) : Result<List<MarvelCharacterSeries>>
}


