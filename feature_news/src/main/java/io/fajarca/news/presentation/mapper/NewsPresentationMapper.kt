package io.fajarca.news.presentation.mapper

import io.fajarca.news.domain.entities.News
import io.fajarca.presentation.extension.toLocalTime
import java.util.*
import javax.inject.Inject

class NewsPresentationMapper @Inject constructor(){

    fun map(input: News, locale: Locale) : News {
        return News(input.title, input.url, input.imageUrl, input.publishedAt.toLocalTime(locale), input.category, input.sourceId, input.sourceName)
    }

}