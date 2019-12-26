package io.fajarca.marvel.data.service

import io.fajarca.marvel.domain.model.response.CharacterDto
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("v1/public/characters")
    suspend fun getCharacters(@Query("orderBy") orderBy: String = "-modified"): CharacterDto

}