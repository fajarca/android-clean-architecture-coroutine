package io.fajarca.home.data

import io.fajarca.home.data.response.CharacterDetailDto
import io.fajarca.home.data.response.CharacterDto
import io.fajarca.home.data.response.CharacterSeriesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NewsService {

    @GET("v1/public/characters")
    suspend fun getCharacters(@Query("orderBy") orderBy: String = "-modified"): CharacterDto


    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacterDetail(@Path("characterId") characterId : Int): CharacterDetailDto


    @GET("v1/public/characters/{characterId}/series")
    suspend fun getCharacterSeries(@Path("characterId") characterId : Int): CharacterSeriesDto
}