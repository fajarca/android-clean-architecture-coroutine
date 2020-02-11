package io.fajarca.news.data.response


import com.squareup.moshi.Json

data class NewsDto(
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
        val urlToImage: String? = null,
        @Json(name = "source")
        val source : Source? = null
    ) {
        data class Source(
            @Json(name = "id")
            val id: String? = null,
            @Json(name = "name")
            val name : String? = null
        )
    }
}