package io.fajarca.news.domain.usecase

import io.fajarca.news.domain.repository.NewsRepository
import javax.inject.Inject

class InsertNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend operator fun invoke(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction : () -> Unit) {
        repository.findAllNews(country, category, page, pageSize, onSuccessAction)
    }

 }