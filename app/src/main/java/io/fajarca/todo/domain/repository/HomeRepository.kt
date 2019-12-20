package io.fajarca.todo.domain.repository

import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.response.CharacterDto

interface HomeRepository {
    suspend fun getAllCharacters() : Result<CharacterDto>
}


