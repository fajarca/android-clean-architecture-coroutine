package io.fajarca.home.data

import io.fajarca.core.common.Mapper
import io.fajarca.core.database.CharacterEntity
import io.fajarca.home.domain.MarvelCharacter
import kotlinx.coroutines.flow.Flow

class CharactersMapper : Mapper<CharacterDto, List<CharacterEntity>>(){

    override fun map(input: CharacterDto): List<CharacterEntity>{
        val chars = mutableListOf<CharacterEntity>()
        input.data.results.forEach {
            chars.add(CharacterEntity(it.id, it.name, "${it.thumbnail.path}/portrait_uncanny.${it.thumbnail.extension}"))
        }
        return chars
    }
}