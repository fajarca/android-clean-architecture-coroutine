package io.fajarca.news.data

import io.fajarca.news.data.response.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("v2/top-headlines")
    suspend fun getNews(@Query("country") country : String?,
                        @Query("category") category : String?,
                                @Query("page") page: Int,
                                @Query("pageSize") pageSize: Int): NewsDto


}