package io.fajarca.home.domain.repository

import io.fajarca.core.database.TopHeadlineEntity
import io.fajarca.core.vo.Result
import io.fajarca.home.data.response.NewsDto
import io.fajarca.home.domain.entities.News

interface NewsRepository {
    suspend fun getNewsFromApi(country : String = "id", page : Int = 1, pageSize : Int = 25) : Result<NewsDto>
    suspend fun getNewsFromDb() : List<TopHeadlineEntity>
    suspend fun getNews() : List<News>
    suspend fun insertNews(topHeadlines : List<TopHeadlineEntity>)
}


