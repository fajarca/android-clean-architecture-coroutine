package io.fajarca.todo.domain.repository

import io.fajarca.todo.domain.model.Character

interface HomeRepository {
    suspend fun getAllCharacters() : List<Character>
}


