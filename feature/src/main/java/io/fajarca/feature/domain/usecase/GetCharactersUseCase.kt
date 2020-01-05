package io.fajarca.feature.domain.usecase

import io.fajarca.core.database.Character
import io.fajarca.feature.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend fun execute() : Flow<List<Character>> {
        return repository.getAllCharacter()
    }

}