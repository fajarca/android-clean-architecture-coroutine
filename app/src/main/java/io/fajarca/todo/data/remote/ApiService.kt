package io.fajarca.todo.data.remote

import io.fajarca.todo.data.remote.response.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("v1/public/characters")
    suspend fun getCharacters(@Query("orderBy") orderBy: String = "-modified"): Response<CharactersResponse>

}