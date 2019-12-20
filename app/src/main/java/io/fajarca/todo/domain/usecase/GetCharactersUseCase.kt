package io.fajarca.todo.domain.usecase

import io.fajarca.todo.data.repository.HomeRepositoryImpl
import io.fajarca.todo.domain.model.response.CharactersResponse
import io.fajarca.todo.domain.model.Result
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: HomeRepositoryImpl)  {

    suspend fun execute() : Result<CharactersResponse> {
        return repository.getAllCharacters()
    }

}