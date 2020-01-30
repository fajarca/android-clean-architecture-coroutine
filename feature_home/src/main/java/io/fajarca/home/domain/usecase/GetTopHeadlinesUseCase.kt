package io.fajarca.home.domain.usecase

import io.fajarca.home.domain.entities.TopHeadline
import io.fajarca.home.domain.repository.NewsRepository
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(): List<TopHeadline> {
        return repository.getHeadlines()
    }

}