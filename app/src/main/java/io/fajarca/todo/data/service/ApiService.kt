package io.fajarca.todo.data.service

import io.fajarca.todo.domain.model.response.CharacterDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("v1/public/characters")
    suspend fun getCharacters(@Query("orderBy") orderBy: String = "-modified"): CharacterDto

}