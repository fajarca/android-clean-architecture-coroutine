package io.fajarca.news_category.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.news_category.R
import io.fajarca.news_category.databinding.ItemNewsCategoryBinding
import io.fajarca.news_category.presentation.model.NewsCategory

class NewsCategoryRecyclerAdapter(private val listener: NewsCategoryClickListener) : ListAdapter<NewsCategory, NewsCategoryRecyclerAdapter.NewsChannelViewHolder>(
    diffCallback
) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsChannelViewHolder {
        return NewsChannelViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: NewsChannelViewHolder, position: Int) {
       holder.bind(getItem(position) ?: NewsCategory(
           "",
           "",
           R.drawable.ic_business
       ), listener)
    }

    class NewsChannelViewHolder(private val binding: ItemNewsCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: NewsCategory, listener: NewsCategoryClickListener) {
            binding.category = category
            binding.imageView.setImageResource(category.resourceId)
            binding.root.setOnClickListener { listener.onNewsCategoryPressed(category) }
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): NewsChannelViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsCategoryBinding.inflate(layoutInflater, parent,false)
                return NewsChannelViewHolder(
                    binding
                )
            }
        }
    }
    

    interface NewsCategoryClickListener {
        fun onNewsCategoryPressed(category: NewsCategory)
    }

    
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<NewsCategory>() {
            override fun areItemsTheSame(oldItem: NewsCategory, newItem: NewsCategory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsCategory, newItem: NewsCategory): Boolean {
                return oldItem == newItem
            }

        }
    }
}