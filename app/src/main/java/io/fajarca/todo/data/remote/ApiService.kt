package io.fajarca.todo.data.remote

import io.fajarca.todo.data.remote.response.NowPlayingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("now_playing")
    suspend fun nowPlaying(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<NowPlayingResponse>

}