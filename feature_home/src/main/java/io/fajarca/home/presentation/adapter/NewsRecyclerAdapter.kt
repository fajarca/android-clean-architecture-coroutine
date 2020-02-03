package io.fajarca.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.home.databinding.ItemNewsBinding
import io.fajarca.home.domain.entities.News

class NewsRecyclerAdapter(private val listener: NewsClickListener) : PagedListAdapter<News, NewsRecyclerAdapter.NewsViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position) ?: News("", "","","","",""), listener)
    }


    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News, listener: NewsClickListener) {
            binding.news = news
            binding.root.setOnClickListener { listener.onNewsPressed(news) }
            binding.executePendingBindings()
        }
    }

    interface NewsClickListener {
        fun onNewsPressed(news: News)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }

        }
    }
}