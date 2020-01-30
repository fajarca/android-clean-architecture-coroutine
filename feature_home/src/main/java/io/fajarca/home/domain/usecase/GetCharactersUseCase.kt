package io.fajarca.home.domain.usecase

import io.fajarca.home.domain.entities.MarvelCharacter
import io.fajarca.home.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend fun execute(): List<MarvelCharacter> {
        return repository.getAllCharacter()
    }

}