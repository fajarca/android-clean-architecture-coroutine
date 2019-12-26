package io.fajarca.todo.data.mapper

import io.fajarca.todo.domain.model.response.CharacterDto
import io.fajarca.todo.domain.model.local.Character

class CharactersMapper {

    fun mapToCharacterEntity(characters : CharacterDto) : List<Character> {
        val chars = mutableListOf<Character>()
        characters.data.results.forEach {
            chars.add(Character(it.id, it.name, "${it.thumbnail.path}/portrait_uncanny.${it.thumbnail.extension}"))
        }
        return chars
    }
}