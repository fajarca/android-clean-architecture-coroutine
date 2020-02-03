package io.fajarca.home.domain.usecase

import io.fajarca.home.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(page : Int, pageSize : Int, onSuccess : () -> Unit) {
         repository.findAllNews("id", page, pageSize, onSuccess)
    }

    fun getNewsFactory() = repository.getNewsFactory()


}