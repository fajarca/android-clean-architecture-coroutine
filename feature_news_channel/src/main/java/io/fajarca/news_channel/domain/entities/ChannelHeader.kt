package io.fajarca.news_channel.domain.entities

class ChannelHeader(val name : String) : NewsChannelItem() {
    override fun getType() : Int {
        return HEADER_TYPE
    }
}