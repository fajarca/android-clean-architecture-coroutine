package io.fajarca.todo.data.repository

import io.fajarca.todo.data.remote.HomeRemoteDataSource
import javax.inject.Inject

class HomeRepository @Inject constructor(private val remoteDataSource: HomeRemoteDataSource) {

    suspend fun getAllCharacters() = remoteDataSource.getAllCharacters()

}


