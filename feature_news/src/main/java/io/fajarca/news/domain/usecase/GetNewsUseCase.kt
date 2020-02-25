package io.fajarca.news.domain.usecase

import androidx.paging.DataSource
import io.fajarca.news.domain.entities.News
import io.fajarca.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction : () -> Unit) {
         repository.findAllNews(country, category, page, pageSize, onSuccessAction)
    }

    fun getNewsFactory(country: String?, category: String?): DataSource.Factory<Int, News> {
        return if (!country.isNullOrEmpty()) {
            repository.findByCountry(country)
        } else {
            repository.findByCategory(category)
        }
    }


}