package io.fajarca.home.domain.usecase

import io.fajarca.home.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(country : String, page : Int, pageSize : Int, onSuccessAction : () -> Unit) {
         repository.findAllNews(country, page, pageSize, onSuccessAction)
    }

    fun getNewsFactory() = repository.getNewsFactory()


}