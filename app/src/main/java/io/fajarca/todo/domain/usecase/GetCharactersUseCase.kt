package io.fajarca.todo.domain.usecase

import io.fajarca.todo.data.repository.HomeRepositoryImpl
import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.response.CharacterDto
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: HomeRepositoryImpl)  {

    suspend fun execute() : List<Character> {
        return repository.getAllCharacters()
    }

}