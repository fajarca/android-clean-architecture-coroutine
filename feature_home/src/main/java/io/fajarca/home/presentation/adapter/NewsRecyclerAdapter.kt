package io.fajarca.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.home.databinding.ItemNewsBinding
import io.fajarca.home.domain.entities.TopHeadline

class NewsRecyclerAdapter(private val listener: NewsClickListener) : ListAdapter<TopHeadline, NewsRecyclerAdapter.NewsViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }


    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: TopHeadline, listener: NewsClickListener) {
            binding.news = news
            binding.root.setOnClickListener { listener.onNewsPressed(news) }
            binding.executePendingBindings()
        }
    }

    interface NewsClickListener {
        fun onNewsPressed(news: TopHeadline)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<TopHeadline>() {
            override fun areItemsTheSame(oldItem: TopHeadline, newItem: TopHeadline): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TopHeadline, newItem: TopHeadline): Boolean {
                return oldItem == newItem
            }

        }
    }
}