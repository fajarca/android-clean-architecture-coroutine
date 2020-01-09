package io.fajarca.home.domain.usecase

import io.fajarca.core.database.Character
import io.fajarca.home.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase (private val repository: CharacterRepository) {

    suspend fun execute() : Flow<List<Character>> {
        return repository.getAllCharacter()
    }

}