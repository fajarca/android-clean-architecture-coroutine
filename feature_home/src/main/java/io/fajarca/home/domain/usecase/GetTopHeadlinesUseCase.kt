package io.fajarca.home.domain.usecase

import io.fajarca.home.domain.entities.News
import io.fajarca.home.domain.repository.NewsRepository
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(limit : Int): List<News> {
        return repository.getTopHeadlines(limit)
    }

}