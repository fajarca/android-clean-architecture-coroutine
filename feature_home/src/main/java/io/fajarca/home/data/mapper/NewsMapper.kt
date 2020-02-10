package io.fajarca.home.data.mapper

import io.fajarca.core.database.entity.NewsEntity
import io.fajarca.home.data.response.NewsDto
import io.fajarca.home.domain.entities.News

class NewsMapper {

    fun map(country: String?, category: String?, input: NewsDto): List<NewsEntity> {
        val headlines = mutableListOf<NewsEntity>()
        input.articles?.map {
            headlines.add(
                NewsEntity(
                    it?.title ?: "",
                    it?.url ?: "",
                    it?.urlToImage ?: "",
                    country ?: "",
                    category ?: "",
                    it?.publishedAt ?: "",
                    it?.source?.id ?: "",
                    it?.source?.name ?: ""
                )
            )
        }
        return headlines
    }

    fun mapToDomain(input: NewsEntity): News {
        return News(
            input.title,
            input.url,
            input.imageUrl,
            input.publishedAt,
            input.sourceId,
            input.sourceName
        )
    }
}