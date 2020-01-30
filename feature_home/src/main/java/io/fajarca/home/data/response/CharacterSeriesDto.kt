package io.fajarca.home.data.response


import com.squareup.moshi.Json

data class CharacterSeriesDto(
    @Json(name = "data")
    val `data`: Data
) {
    data class Data(
        @Json(name = "results")
        val results: List<Result>
    ) {
        data class Result(
            @Json(name = "characters")
            val characters: Characters,
            @Json(name = "endYear")
            val endYear: Int,
            @Json(name = "id")
            val id: Int,
            @Json(name = "rating")
            val rating: String,
            @Json(name = "startYear")
            val startYear: Int,
            @Json(name = "thumbnail")
            val thumbnail: Thumbnail,
            @Json(name = "title")
            val title: String,
            @Json(name = "urls")
            val urls: List<Url>
        ) {
            data class Characters(
                @Json(name = "items")
                val items: List<Item>
            ) {
                data class Item(
                    @Json(name = "name")
                    val name: String
                )
            }

            data class Thumbnail(
                @Json(name = "extension")
                val extension: String,
                @Json(name = "path")
                val path: String
            )

            data class Url(
                @Json(name = "type")
                val type: String,
                @Json(name = "url")
                val url: String
            )
        }
    }
}