package io.fajarca.marvel.data.mapper

import io.fajarca.marvel.domain.model.local.Character
import io.fajarca.marvel.domain.model.response.CharacterDto

class CharactersMapper : Mapper<CharacterDto, List<Character>>(){

    override fun map(input: CharacterDto): List<Character>{
        val chars = mutableListOf<Character>()
        input.data.results.forEach {
            chars.add(Character(it.id, it.name, "${it.thumbnail.path}/portrait_uncanny.${it.thumbnail.extension}"))
        }
        return chars
    }
}