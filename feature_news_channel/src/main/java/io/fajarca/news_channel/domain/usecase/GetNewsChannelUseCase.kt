package io.fajarca.news_channel.domain.usecase

import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.repository.NewsChannelRepository
import javax.inject.Inject

class GetNewsChannelUseCase @Inject constructor(private val repository: NewsChannelRepository) {

    suspend operator fun invoke(): List<NewsChannel> {
        return repository.findAllNewsChannel()
    }

}