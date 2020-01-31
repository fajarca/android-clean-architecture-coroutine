package io.fajarca.home.data

import io.fajarca.home.data.response.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String = "id",
                                @Query("page") page: Int = 1,
                                @Query("pageSize") pageSize: Int = 25): NewsDto


}