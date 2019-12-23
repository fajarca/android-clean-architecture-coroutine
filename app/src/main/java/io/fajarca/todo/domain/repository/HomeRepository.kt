package io.fajarca.todo.domain.repository

import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.common.Result

interface HomeRepository {
    suspend fun getAllCharacters() : Result<List<Character>>
}


