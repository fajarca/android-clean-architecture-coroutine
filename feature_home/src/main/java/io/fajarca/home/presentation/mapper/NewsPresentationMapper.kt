package io.fajarca.home.presentation.mapper

import io.fajarca.core.mapper.Mapper
import io.fajarca.home.domain.entities.News
import io.fajarca.presentation.extension.toLocalTime
import javax.inject.Inject

class NewsPresentationMapper @Inject constructor(): Mapper<News, News>(){

    override fun map(input: News) : News {
        return News(input.title, input.url, input.imageUrl, input.publishedAt.toLocalTime(), input.category, input.sourceId, input.sourceName)
    }

}