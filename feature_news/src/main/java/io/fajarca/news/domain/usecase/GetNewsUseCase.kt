package io.fajarca.news.domain.usecase

import androidx.paging.DataSource
import io.fajarca.news.domain.entities.News
import io.fajarca.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    operator fun invoke(country : String?, category : String?): DataSource.Factory<Int, News> {
        return if (!country.isNullOrEmpty()) {
            repository.findByCountry(country)
        } else if (!category.isNullOrEmpty()) {
            repository.findByCategory(category)
        } else {
            repository.findAll()
        }
    }

}