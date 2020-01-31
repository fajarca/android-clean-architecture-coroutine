package io.fajarca.home.data.mapper

import io.fajarca.core.mapper.Mapper
import io.fajarca.core.database.NewsEntity
import io.fajarca.home.data.response.NewsDto
import io.fajarca.home.domain.entities.News

class NewsMapper : Mapper<NewsDto, List<NewsEntity>>(){

    override fun map(input: NewsDto): List<NewsEntity>{
        val headlines = mutableListOf<NewsEntity>()
        input.articles?.map {
            headlines.add(NewsEntity(it?.title ?: "", it?.url ?: "", it?.urlToImage ?: "", it?.publishedAt ?: "", it?.source?.id ?: "", it?.source?.name ?: ""))
        }
        return headlines
    }

    fun mapToDomain(input : List<NewsEntity>) : List<News> {
        val headlines = mutableListOf<News>()
        input.map {
            headlines.add(News(it.id, it.title, it.url, it.imageUrl, it.publishedAt, it.sourceId, it.sourceName))
        }
        return headlines
    }
}