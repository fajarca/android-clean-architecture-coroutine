package io.fajarca.todo.data.source.remote

import io.fajarca.todo.base.BaseRemoteDataSource
import io.fajarca.todo.data.service.ApiService
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(private val api: ApiService) : BaseRemoteDataSource() {

    suspend fun getAllCharacters() = getApiResult {  api.getCharacters() }

}