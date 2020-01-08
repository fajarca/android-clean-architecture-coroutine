package io.fajarca.home.data

import io.fajarca.core.common.Mapper
import io.fajarca.core.network.CharacterDto
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