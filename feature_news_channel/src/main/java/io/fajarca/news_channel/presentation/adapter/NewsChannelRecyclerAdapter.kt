package io.fajarca.news_channel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.news_channel.databinding.ItemNewsChannelBinding
import io.fajarca.news_channel.domain.entities.NewsChannel

class NewsChannelRecyclerAdapter(private val listener: NewsChannelClickListener) : ListAdapter<NewsChannel, NewsChannelRecyclerAdapter.NewsChannelViewHolder>(
    diffCallback
) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsChannelViewHolder {
        return NewsChannelViewHolder.create(parent) 
    }

    override fun onBindViewHolder(holder: NewsChannelViewHolder, position: Int) {
       holder.bind(getItem(position) ?: NewsChannel("","","",""), listener)
    }

    class NewsChannelViewHolder(private val binding: ItemNewsChannelBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(channel: NewsChannel, listener: NewsChannelClickListener) {
            binding.channel = channel
            binding.root.setOnClickListener { listener.onNewsChannelPressed(channel) }
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): NewsChannelViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsChannelBinding.inflate(layoutInflater, parent,false)
                return NewsChannelViewHolder(binding)
            }
        }
    }
    

    interface NewsChannelClickListener {
        fun onNewsChannelPressed(news: NewsChannel)
    }

    
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<NewsChannel>() {
            override fun areItemsTheSame(oldItem: NewsChannel, newItem: NewsChannel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsChannel, newItem: NewsChannel): Boolean {
                return oldItem == newItem
            }

        }
    }
}