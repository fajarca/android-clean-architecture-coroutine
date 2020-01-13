package io.fajarca.feature.data

import io.fajarca.core.common.CoroutineDispatcherProvider
import io.fajarca.core.common.Result
import io.fajarca.feature.domain.CharacterDetail
import io.fajarca.feature.domain.repository.CharacterDetailRepository
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper : CharacterDetailMapper,
    private val remoteDataSource: CharacterDetailRemoteDataSource
) : CharacterDetailRepository {

    override suspend fun getCharacterDetail(characterId: Int): Result<CharacterDetail> {
        val apiResult = remoteDataSource.getCharacterDetail(characterId, dispatcher.io)
        return mapper.map(apiResult)
    }

}