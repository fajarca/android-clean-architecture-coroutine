package io.fajarca.characters.domain.usecase

import io.fajarca.characters.domain.MarvelCharacter
import io.fajarca.characters.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend fun execute(): List<MarvelCharacter> {
        return repository.getAllCharacter()
    }

}