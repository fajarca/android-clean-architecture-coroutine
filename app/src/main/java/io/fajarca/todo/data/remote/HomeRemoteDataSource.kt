package io.fajarca.todo.data.remote

import io.fajarca.todo.base.BaseRemoteDataSource
import io.fajarca.todo.data.remote.ApiService
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(private val api: ApiService) : BaseRemoteDataSource() {

    suspend fun nowPlaying() = getApiResult {  api.nowPlaying() }

}