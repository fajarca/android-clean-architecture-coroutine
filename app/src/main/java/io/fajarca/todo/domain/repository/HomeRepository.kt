package io.fajarca.todo.domain.repository

import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.local.Character
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getAllCharactersFromApi() : Result<List<Character>>
    suspend fun getAllCharactersFromDb() : Flow<List<Character>>
    suspend fun getAllCharacter() : Flow<List<Character>>
    suspend fun insertAllCharacter(characters : List<Character>)
}


