package io.fajarca.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.core.vo.UiState
import io.fajarca.home.databinding.ItemFooterBinding
import io.fajarca.home.databinding.ItemHeadlineBinding
import io.fajarca.home.databinding.ItemNewsBinding
import io.fajarca.home.domain.entities.News
import retrofit2.http.HEAD

class NewsRecyclerAdapter(private val listener: NewsClickListener) : PagedListAdapter<News, RecyclerView.ViewHolder>(
    diffCallback
) {

    private val DATA_TYPE = 0
    private val FOOTER_TYPE = 1
    private val HEADLINE_TYPE = 2
    private var state : UiState = UiState.Loading

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADLINE_TYPE) HeadlineViewHolder.create(parent) else if (viewType == DATA_TYPE) NewsViewHolder.create(parent) else FooterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == HEADLINE_TYPE) (holder as HeadlineViewHolder).bind(getItem(position) ?: News("","","","","","", ""), listener) else if (viewType == DATA_TYPE) (holder as NewsViewHolder).bind(getItem(position) ?: News("","","","","","", ""), listener) else (holder as FooterViewHolder).bind()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADLINE_TYPE else if(position < super.getItemCount()) DATA_TYPE else FOOTER_TYPE
    }

    class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News, listener: NewsClickListener) {
            binding.news = news
            binding.root.setOnClickListener { listener.onNewsPressed(news) }
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): NewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsBinding.inflate(layoutInflater, parent,false)
                return NewsViewHolder(binding)
            }
        }
    }

    class FooterViewHolder(private val binding: ItemFooterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): FooterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFooterBinding.inflate(layoutInflater, parent,false)
                return FooterViewHolder(binding)
            }
        }
    }

    class HeadlineViewHolder(private val binding: ItemHeadlineBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(headline: News, listener: NewsClickListener) {
            binding.headline = headline
            binding.root.setOnClickListener { listener.onHeadlinePressed(headline) }
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): HeadlineViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHeadlineBinding.inflate(layoutInflater, parent,false)
                return HeadlineViewHolder(binding)
            }
        }
    }

    interface NewsClickListener {
        fun onNewsPressed(news: News)
        fun onHeadlinePressed(headline : News)
    }


    private fun hasFooter() : Boolean {
        return super.getItemCount() != 0 && (state == UiState.Loading)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    fun setState(state : UiState) {
        this.state = state
        notifyItemChanged(super.getItemCount())
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