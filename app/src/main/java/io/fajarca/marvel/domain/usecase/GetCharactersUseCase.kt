package io.fajarca.marvel.domain.usecase

import io.fajarca.marvel.data.repository.CharacterRepositoryImpl
import io.fajarca.marvel.domain.model.local.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepositoryImpl) {

    suspend fun execute() : Flow<List<Character>> {
        return repository.getAllCharacter()
    }

}