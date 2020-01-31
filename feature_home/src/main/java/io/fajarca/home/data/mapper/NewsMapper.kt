package io.fajarca.home.data.mapper

import io.fajarca.core.mapper.Mapper
import io.fajarca.core.database.TopHeadlineEntity
import io.fajarca.home.data.response.NewsDto
import io.fajarca.home.domain.entities.News

class NewsMapper : Mapper<NewsDto, List<TopHeadlineEntity>>(){

    override fun map(input: NewsDto): List<TopHeadlineEntity>{
        val headlines = mutableListOf<TopHeadlineEntity>()
        input.articles?.map {
            headlines.add(TopHeadlineEntity(it?.title ?: "", it?.url ?: "", it?.urlToImage ?: "", it?.publishedAt ?: "", it?.source?.id ?: "", it?.source?.name ?: ""))
        }
        return headlines
    }

    fun mapToDomain(input : List<TopHeadlineEntity>) : List<News> {
        val headlines = mutableListOf<News>()
        input.map {
            headlines.add(News(it.id, it.title, it.url, it.imageUrl, it.publishedAt, it.sourceId, it.sourceName))
        }
        return headlines
    }
}