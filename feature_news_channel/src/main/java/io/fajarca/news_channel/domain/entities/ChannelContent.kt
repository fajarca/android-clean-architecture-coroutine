package io.fajarca.news_channel.domain.entities

data class ChannelContent(val newsChannel: NewsChannel) : NewsChannelItem() {
    override fun getType() : Int {
        return CONTENT_TYPE
    }
}