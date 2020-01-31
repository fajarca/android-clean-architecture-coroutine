package io.fajarca.home.presentation.mapper

import io.fajarca.core.mapper.Mapper
import io.fajarca.home.domain.entities.News
import io.fajarca.presentation.extension.toLocalTime
import javax.inject.Inject

class NewsPresentationMapper @Inject constructor(): Mapper<List<News>, List<News>>(){

    override fun map(input: List<News>) : List<News> {
        val mappedHeadlines = mutableListOf<News>()
        input.map {
            mappedHeadlines.add(News(it.title, it.url, it.imageUrl, it.publishedAt.toLocalTime(), it.sourceId, it.sourceName))
        }
        return mappedHeadlines
    }

}