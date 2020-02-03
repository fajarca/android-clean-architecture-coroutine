package io.fajarca.home.domain.usecase

import io.fajarca.core.database.NewsEntity
import io.fajarca.home.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(page : Int, pageSize : Int): List<NewsEntity> {
        return repository.getNewsFromApi("id", page, pageSize)
    }

}