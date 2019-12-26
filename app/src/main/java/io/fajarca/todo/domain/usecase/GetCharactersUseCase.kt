package io.fajarca.todo.domain.usecase

import io.fajarca.todo.data.repository.CharacterRepositoryImpl
import io.fajarca.todo.domain.model.local.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepositoryImpl) {

    suspend fun execute() : Flow<List<Character>> {
        return repository.getAllCharacter()
    }

}