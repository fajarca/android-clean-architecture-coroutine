package io.fajarca.todo.domain.repository

import io.fajarca.todo.domain.model.response.CharactersResponse
import io.fajarca.todo.domain.model.Result

interface HomeRepository {
    suspend fun getAllCharacters() : Result<CharactersResponse>
}


