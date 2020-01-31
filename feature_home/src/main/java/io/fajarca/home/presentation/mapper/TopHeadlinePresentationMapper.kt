package io.fajarca.home.presentation.mapper

import io.fajarca.core.mapper.Mapper
import io.fajarca.home.domain.entities.TopHeadline
import io.fajarca.presentation.extension.toLocalTime
import javax.inject.Inject

class TopHeadlinePresentationMapper @Inject constructor(): Mapper<List<TopHeadline>, List<TopHeadline>>(){

    override fun map(input: List<TopHeadline>) : List<TopHeadline> {
        val mappedHeadlines = mutableListOf<TopHeadline>()
        input.map {
            mappedHeadlines.add(TopHeadline(it.id, it.title, it.url, it.imageUrl, it.publishedAt.toLocalTime(), it.sourceId, it.sourceName))
        }
        return mappedHeadlines
    }

}