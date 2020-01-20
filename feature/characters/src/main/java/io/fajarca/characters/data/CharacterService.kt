package io.fajarca.characters.data

import io.fajarca.characters.data.response.CharacterDetailDto
import io.fajarca.characters.data.response.CharacterDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CharacterService {

    @GET("v1/public/characters")
    suspend fun getCharacters(@Query("orderBy") orderBy: String = "-modified"): CharacterDto


    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacterDetail(@Path("characterId") characterId : Int): CharacterDetailDto
}