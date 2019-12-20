package io.fajarca.todo.domain.model.response

import com.google.gson.annotations.SerializedName

data class NowPlayingResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<NowPlaying>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)


data class NowPlaying(

    @SerializedName("id")
    var id: Long = 0,

    @SerializedName("title")
    var title: String = "",

    @SerializedName("original_title")
    var originalTitle: String = "",

    @SerializedName("overview")
    var overview: String = "",

    @SerializedName("backdrop_path")
    var backdropPath: String? = "",

    @SerializedName("poster_path")
    var posterPath: String = "",

    @SerializedName("original_language")
    var originalLanguage: String = "",

    @SerializedName("popularity")
    var popularity: Double = 0.0,

    @SerializedName("vote_count")
    var voteCount: Long = 0,

    @SerializedName("vote_average")
    var voteAverage: Float = 0.0f,

    @SerializedName("video")
    var video: Boolean = false,

    @SerializedName("adult")
    var adult: Boolean = false,

    @SerializedName("release_date")
    var releaseDate: String = "",

    @SerializedName("genre_ids")
    var genresId: List<Int> = emptyList()

)
