package io.fajarca.todo.data.repository

import io.fajarca.todo.data.mapper.CharactersMapper
import io.fajarca.todo.data.source.remote.HomeRemoteDataSource
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.domain.model.response.CharacterDto
import io.fajarca.todo.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val remoteDataSource: HomeRemoteDataSource, private val mapper : CharactersMapper) :
    HomeRepository {

    override suspend fun getAllCharacters(): Result<CharacterDto> {
        return remoteDataSource.getAllCharacters()
    }

}