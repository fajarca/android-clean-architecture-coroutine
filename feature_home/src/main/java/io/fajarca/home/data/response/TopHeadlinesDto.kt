package io.fajarca.home.data.response


import com.squareup.moshi.Json

data class TopHeadlinesDto(
    @Json(name = "articles")
    val articles: List<Article?>? = null
) {
    data class Article(
        @Json(name = "publishedAt")
        val publishedAt: String? = null,
        @Json(name = "title")
        val title: String? = null,
        @Json(name = "url")
        val url: String? = null,
        @Json(name = "urlToImage")
        val urlToImage: String? = null
    )
}