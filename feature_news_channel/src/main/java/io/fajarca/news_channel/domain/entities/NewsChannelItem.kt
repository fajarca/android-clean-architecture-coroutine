package io.fajarca.news_channel.domain.entities

abstract class NewsChannelItem {
    companion object {
        const val HEADER_TYPE = 1
        const val CONTENT_TYPE = 2
    }
    abstract fun getType() : Int
}