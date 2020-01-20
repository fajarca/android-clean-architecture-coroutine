package io.fajarca.characters.data

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("v1/public/characters")
    suspend fun getCharacters(@Query("orderBy") orderBy: String = "-modified"): CharacterDto

}