package io.fajarca.home.domain.repository

import io.fajarca.core.database.TopHeadlineEntity
import io.fajarca.core.vo.Result
import io.fajarca.home.data.response.TopHeadlinesDto
import io.fajarca.home.domain.entities.TopHeadline

interface NewsRepository {
    suspend fun getHeadlinesFromApi() : Result<TopHeadlinesDto>
    suspend fun getHeadlinesFromDb() : List<TopHeadlineEntity>
    suspend fun getHeadlines() : List<TopHeadline>
    suspend fun insertHeadlines(characters : List<TopHeadlineEntity>)
}


