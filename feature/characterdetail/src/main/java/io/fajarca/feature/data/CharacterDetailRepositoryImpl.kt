package io.fajarca.feature.data

import io.fajarca.core.common.CoroutineDispatcherProvider
import io.fajarca.core.common.Result
import io.fajarca.feature.domain.repository.CharacterDetailRepository
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val remoteDataSource: CharacterDetailRemoteDataSource
) : CharacterDetailRepository {

    override suspend fun getCharacterDetail(characterId: Int): Result<CharacterDetailDto> {
        return remoteDataSource.getCharacterDetail(characterId, dispatcher.io)
    }

}