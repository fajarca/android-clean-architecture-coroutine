package io.fajarca.home.data.mapper

import io.fajarca.core.mapper.Mapper
import io.fajarca.core.database.TopHeadlineEntity
import io.fajarca.home.data.response.TopHeadlinesDto
import io.fajarca.home.domain.entities.TopHeadline

class TopHeadlineMapper : Mapper<TopHeadlinesDto, List<TopHeadlineEntity>>(){

    override fun map(input: TopHeadlinesDto): List<TopHeadlineEntity>{
        val headlines = mutableListOf<TopHeadlineEntity>()
        input.articles?.map {
            headlines.add(TopHeadlineEntity(it?.title ?: "", it?.url ?: "", it?.urlToImage ?: "", it?.publishedAt ?: "", it?.source?.id ?: "", it?.source?.name ?: ""))
        }
        return headlines
    }

    fun mapToDomain(input : List<TopHeadlineEntity>) : List<TopHeadline> {
        val headlines = mutableListOf<TopHeadline>()
        input.map {
            headlines.add(TopHeadline(it.id, it.title, it.url, it.imageUrl, it.publishedAt, it.sourceId, it.sourceName))
        }
        return headlines
    }
}