package io.fajarca.home.domain.usecase

import io.fajarca.core.common.UseCase
import io.fajarca.core.database.CharacterEntity
import io.fajarca.home.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor (private val repository: CharacterRepository) : UseCase<List<CharacterEntity>>() {

    override suspend fun execute(onSuccess : (it : List<CharacterEntity>) -> Unit, onError : (throwable : Throwable) -> Unit, onEmpty : () -> Unit) {
        return repository.getAllCharacter()
            .catch { throwable ->
                onError(throwable)
            }
            .collect {
                if (it.isEmpty()) onEmpty() else onSuccess(it)
            }
    }

}