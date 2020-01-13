package io.fajarca.feature.data

import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacterDetail(@Path("characterId") characterId : Int): CharacterDetailDto

}