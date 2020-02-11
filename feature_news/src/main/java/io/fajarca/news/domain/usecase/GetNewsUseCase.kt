package io.fajarca.news.domain.usecase

import io.fajarca.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction : () -> Unit) {
         repository.findAllNews(country, category, page, pageSize, onSuccessAction)
    }

    fun getNewsFactory(country : String?, category : String?) = repository.getNewsFactory(country, category)


}