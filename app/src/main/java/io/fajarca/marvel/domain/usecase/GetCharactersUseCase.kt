package io.fajarca.marvel.domain.usecase

import io.fajarca.marvel.domain.model.local.Character
import io.fajarca.marvel.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend fun execute() : Flow<List<Character>> {
        return repository.getAllCharacter()
    }

}