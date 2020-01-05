package io.fajarca.feature.data.mapper

import io.fajarca.core.network.response.CharacterDto
import io.fajarca.core.database.Character

class CharactersMapper : Mapper<CharacterDto, List<Character>>(){

    override fun map(input: CharacterDto): List<Character>{
        val chars = mutableListOf<Character>()
        input.data.results.forEach {
            chars.add(Character(it.id, it.name, "${it.thumbnail.path}/portrait_uncanny.${it.thumbnail.extension}"))
        }
        return chars
    }
}