package io.fajarca.todo.data.mapper

import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.response.CharacterDto

class CharactersMapper {

    fun map(characters : CharacterDto) : List<Character> {
        val chars = mutableListOf<Character>()
        characters.data.results.forEach {
            chars.add(Character(it.id, it.name, "${it.thumbnail.path}/portrait_uncanny.${it.thumbnail.extension}"))
        }
        return chars
    }
}