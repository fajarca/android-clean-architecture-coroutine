package io.fajarca.news.domain.usecase

import io.fajarca.core.vo.Result
import io.fajarca.news.domain.entities.News
import io.fajarca.news.domain.repository.NewsRepository
import javax.inject.Inject

class RefreshNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend operator fun invoke(country : String?, category : String?): Result<List<News>> {
        return repository.refreshNews(country, category, 1, 10)
    }

}