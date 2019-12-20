package io.fajarca.todo.data.repository

import io.fajarca.todo.data.mapper.CharactersMapper
import io.fajarca.todo.data.source.remote.HomeRemoteDataSource
import io.fajarca.todo.domain.model.response.CharactersResponse
import io.fajarca.todo.domain.repository.HomeRepository
import io.fajarca.todo.domain.model.Result
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val remoteDataSource: HomeRemoteDataSource, private val mapper : CharactersMapper) :
    HomeRepository {

    override suspend fun getAllCharacters(): Result<CharactersResponse> {
        return remoteDataSource.getAllCharacters()
    }

}