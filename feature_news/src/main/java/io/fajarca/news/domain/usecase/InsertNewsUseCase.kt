package io.fajarca.news.domain.usecase

import io.fajarca.core.network.HttpResult
import io.fajarca.news.domain.repository.NewsRepository
import javax.inject.Inject

class InsertNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend operator fun invoke(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction : () -> Unit, onErrorAction:  (cause : HttpResult, code : Int, errorMessage : String) -> Unit) {
        repository.findAllNews(country, category, page, pageSize, onSuccessAction, onErrorAction)
    }

 }