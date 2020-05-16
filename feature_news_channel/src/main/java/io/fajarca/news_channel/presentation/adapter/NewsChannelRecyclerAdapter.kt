package io.fajarca.news_channel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.news_channel.databinding.ItemNewsChannelBinding
import io.fajarca.news_channel.databinding.ItemNewsChannelHeaderBinding
import io.fajarca.news_channel.domain.entities.ChannelContent
import io.fajarca.news_channel.domain.entities.ChannelHeader
import io.fajarca.news_channel.domain.entities.NewsChannel
import io.fajarca.news_channel.domain.entities.NewsChannelItem
import io.fajarca.news_channel.domain.entities.NewsChannelItem.Companion.CONTENT_TYPE
import io.fajarca.news_channel.domain.entities.NewsChannelItem.Companion.HEADER_TYPE

class NewsChannelRecyclerAdapter(private val listener: NewsChannelClickListener) : ListAdapter<NewsChannelItem, RecyclerView.ViewHolder>(
    diffCallback
) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_TYPE -> HeaderViewHolder.create(parent)
            CONTENT_TYPE -> ContentViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            HEADER_TYPE -> {
                val viewHolder = holder as HeaderViewHolder
                val header = getItem(position) as ChannelHeader
                viewHolder.bind(header.name)
            }
            CONTENT_TYPE -> {
                val viewHolder = holder as ContentViewHolder
                val content = getItem(position) as ChannelContent
                viewHolder.bind(content, listener)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }

    class HeaderViewHolder(private val binding: ItemNewsChannelHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(headerName: String) {
            binding.name = headerName
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsChannelHeaderBinding.inflate(layoutInflater, parent,false)
                return HeaderViewHolder(binding)
            }
        }
    }
    
    class ContentViewHolder(private val binding: ItemNewsChannelBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(channel: ChannelContent, listener: NewsChannelClickListener) {
            binding.channel = channel
            binding.root.setOnClickListener { listener.onNewsChannelPressed(channel.newsChannel) }
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): ContentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsChannelBinding.inflate(layoutInflater, parent,false)
                return ContentViewHolder(binding)
            }
        }
    }
    

    interface NewsChannelClickListener {
        fun onNewsChannelPressed(channel: NewsChannel)
    }

    
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<NewsChannelItem>() {
            override fun areItemsTheSame(oldItem: NewsChannelItem, newItem: NewsChannelItem): Boolean {
                when (oldItem) {
                    is ChannelHeader -> {
                        val new = newItem as ChannelHeader
                        return oldItem.name == new.name
                    }
                    is ChannelContent -> {
                        val new = newItem as ChannelContent
                        return oldItem.newsChannel.id == new.newsChannel.id
                    }
                    else -> throw IllegalArgumentException("Unknown type")
                }
            }

            override fun areContentsTheSame(oldItem: NewsChannelItem, newItem: NewsChannelItem): Boolean {
                when (oldItem) {
                    is ChannelHeader -> {
                        val new = newItem as ChannelHeader
                        return oldItem.name == new.name
                    }
                    is ChannelContent -> {
                        val new = newItem as ChannelContent
                        return oldItem.newsChannel == new.newsChannel
                    }
                    else -> throw IllegalArgumentException("Unknown type")
                }
            }

        }
    }
}