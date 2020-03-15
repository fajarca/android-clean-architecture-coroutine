package io.fajarca.news_channel.domain.usecase

import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.repository.NewsChannelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsChannelUseCase @Inject constructor(private val repository: NewsChannelRepository) {

    suspend operator fun invoke(): Flow<List<NewsChannel>> {
        return repository.findAllNewsChannel()
    }

}