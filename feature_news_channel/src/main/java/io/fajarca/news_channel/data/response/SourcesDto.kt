package io.fajarca.news_channel.data.response


import com.squareup.moshi.Json

data class SourcesDto(
    @Json(name = "sources")
    val sources: List<Source?>? = null
) {
    data class Source(
        @Json(name = "country")
        val country: String? = null,
        @Json(name = "id")
        val id: String? = null,
        @Json(name = "name")
        val name: String? = null,
        @Json(name = "url")
        val url: String? = null
    )
}